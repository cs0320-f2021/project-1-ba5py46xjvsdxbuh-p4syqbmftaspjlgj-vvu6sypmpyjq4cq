import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

import KDTree.KDTree;
import bloomfilter.BloomFilter;
import bloomfilter.BloomFilterRecommender;
import com.google.common.collect.ImmutableMap;
import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import orm.Skills;
import orm.Trait;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;
import command_code.*;
import recommender.*;


/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {

  // use port 4567 by default when running server
  private static final int DEFAULT_PORT = 4567;

  private HashMap<String, REPLCallable> REPLCommands = new HashMap<>();
  private HashMap<String, Item> traits = new HashMap<>();
  private ArrayList<Skills> skills = new ArrayList<>();
  private KDTree tree = new KDTree();
  private BloomFilterRecommender<Item> filter = new BloomFilterRecommender<>(new HashMap<>(), 0);

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() {
    // set up parsing of command line flags
    OptionParser parser = new OptionParser();

    // "./run --gui" will start a web server
    parser.accepts("gui");

    // use "--port <n>" to specify what port on which the server runs
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
        .defaultsTo(DEFAULT_PORT);

    // turn on the Spark server if "gui" command given
    OptionSet options = parser.parse(args);
    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }

    // add default commands to our REPL hashmap,
    // if an engineer adds wants to add new functionality to the REPL they should add a Java
    // class that implements REPLCallable for it in the command_code package and then insert
    // the command into the REPLCommands HashMap as the other commands are below
    REPLCommands.put("help", new command_code.Help());
    REPLCommands.put("remove_command", new command_code.RemoveCommand());
    REPLCommands.put("recsys_gen_groups", new command_code.RecsysGenGroups());
    REPLCommands.put("recsys_load", new command_code.RecsysLoad());
    REPLCommands.put("recsys_rec", new command_code.RecsysRec());

    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      String input;
      System.out.println("REPL started successfully!");
      System.out.println("Enter commands below or \"help\" at any time to see available commands");
      while ((input = br.readLine()) != null) {
        String[] arguments = null;
        try {
          input = input.trim();
          // This regex splits at spaces except when surrounded by quotes. I learned how to do this
          // from this link https://stackabuse.com/regex-splitting-by-character-unless-in-quotes/
          arguments = input.split(" (?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        } catch (PatternSyntaxException e) {
          System.out.println("ERROR: we couldn't process your input");
          //e.printStackTrace();
        }
        // get the command the user inputted and run it
        try {
          // saves the first argument (which is the command to run) as a REPLCallable
          REPLCallable command = REPLCommands.get(arguments[0]);
          // calls run() on the command, which is the function that all REPLCallable objects
          // are guaranteed to have and that implements the functionality of the command
          command.run(arguments, REPLCommands, traits, skills, tree, filter);
        } catch (ClassCastException e) {
          System.out.println("ERROR: invalid command type");
          //e.printStackTrace();
        } catch (NullPointerException e) {
          System.out.println("ERROR: invalid or null command");
          //e.printStackTrace();
        }
      }
    } catch (IOException e) {
      System.out.println("ERROR: something went wrong with REPL I/O (see: uses of .readLine())");
      //e.printStackTrace();
    }

  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration(Configuration.VERSION_2_3_0);

    // this is the directory where FreeMarker templates are placed
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    // set port to run the server on
    Spark.port(port);

    // specify location of static resources (HTML, CSS, JS, images, etc.)
    Spark.externalStaticFileLocation("src/main/resources/static");

    // when there's a server error, use ExceptionPrinter to display error on GUI
    Spark.exception(Exception.class, new ExceptionPrinter());

    // initialize FreeMarker template engine (converts .ftl templates to HTML)
    FreeMarkerEngine freeMarker = createEngine();

    // setup Spark Routes
    Spark.get("/", new MainHandler(), freeMarker);
  }

  /**
   * Display an error page when an exception occurs in the server.
   */
  private static class ExceptionPrinter implements ExceptionHandler<Exception> {
    @Override
    public void handle(Exception e, Request req, Response res) {
      // status 500 generally means there was an internal server error
      res.status(500);

      // write stack trace to GUI
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }

  /**
   * A handler to serve the site's main page.
   *
   * @return ModelAndView to render.
   * (main.ftl).
   */
  private static class MainHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      // this is a map of variables that are used in the FreeMarker template
      Map<String, Object> variables = ImmutableMap.of("title",
          "Go go GUI");

      return new ModelAndView(variables, "main.ftl");
    }
  }
}


