package command_code;

import java.util.HashMap;

public class RemoveCommand implements REPLCallable {

    public void run(String[] REPLArguments, HashMap<String, REPLCallable> REPLCommands, List<Student> data) {
        if (REPLArguments.length != 2) {
            // run if command syntax is wrong (too many/few arguments were passed in)
            System.out.println("ERROR: invalid syntax for \"remove_command\" command");
            System.out.println("Proper command format: \"remove_command <command_to_be_removed>\"");
        } else if (!REPLCommands.containsKey(REPLArguments[1])) {
            // run if command to be removed doesn't exist
            System.out.println("ERROR: the command you're trying to remove doesn't exist");
        } else {
            REPLCommands.remove(REPLArguments[1]);
            System.out.println("Command removed successfully!");
        }
    }

}
