package lv.jake.jira;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 13, 2010
 * Time: 12:06:29 PM
 *
 * Implements console command propmt for user interaction and enables running of background
 * checking processes.
 */
public class Console {
    private static final String CMD_EXIT = "exit";
    private KostikOMatic matic;
    private static final String CMD_REPORT = "report";

    public static void main(String[] args) {
        final Console console = new Console();
        console.init(args);
        console.runBackgroundProcesses();
        console.startProcessingUserInput();
    }

    private void startProcessingUserInput() {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        boolean exit = false;
        while (!exit) {
            printCommandPromptPrefix();

            final String input;
            try {
                input = bufferedReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException("Unable to read user input", e);
            }
            exit = processUserInput(input);
        }
    }


    private boolean processUserInput(String input) {
        if (CMD_EXIT.equalsIgnoreCase(input)) {
            return true;
        } else if (CMD_REPORT.equalsIgnoreCase(input)) {
            matic.run();
        } else {
            System.out.println("Unrecognized command");
        }
        return false;
    }

    //TODO: Implement
    private void runBackgroundProcesses() {
    }

    private void init(String[] args) {
        matic = new KostikOMatic(args[0]);
        matic.init();
        System.out.println("Jira Issue Watcher");
    }

    private void printCommandPromptPrefix() {
        System.out.println(">>> ");
    }
}
