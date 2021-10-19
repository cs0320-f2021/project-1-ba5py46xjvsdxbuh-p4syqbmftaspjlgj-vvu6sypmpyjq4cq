package command_code;

import java.util.HashMap;

public class RecsysLoad implements REPLCallable {

    public void run(String[] REPLArguments, HashMap<String, REPLCallable> REPLCommands, List<Student> data) {
        // body
        Database db = new Database(REPLArguments[1]);
    }

}
