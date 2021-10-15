package command_code;

import java.util.HashMap;

/**
 * This is the interface that commands which can be run from the REPL must implement
 * so that Main.java is assured they have a run() function with the behavior expected
 * of REPL commands
 */
public interface REPLCallable {

    // REPLArgument is an argument so that the command can do what it
    // needs to given the other command arguments the REPL user has inputted,
    // REPLCommands is an argument so that Help and RemoveCommand can
    // implement their functionality that depends on access to the HashMap
    // of available REPL commands
    public void run(String[] REPLArguments, HashMap<String, REPLCallable> REPLCommands);

}
