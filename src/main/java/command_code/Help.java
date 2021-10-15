package command_code;

import java.util.HashMap;
import java.util.Set;

public class Help implements REPLCallable {

    public void run(String[] REPLArguments, HashMap<String, REPLCallable> REPLCommands) {
        System.out.println("Available Commands:");
        Set<String> keys = REPLCommands.keySet();
        for (String k : keys) {
            System.out.println("\t"+k);
        }
    }

}
