package command_code;

import java.util.HashMap;

public class RecsysLoad implements REPLCallable {

    public void run(String[] REPLArguments, HashMap<String, REPLCallable> REPLCommands) {
        // body
        Database db = new Database(REPLArguments[1]);
        //db.load

    }

}
