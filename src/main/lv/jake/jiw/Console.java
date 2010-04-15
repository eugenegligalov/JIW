package lv.jake.jiw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

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
    private static final String CMD_REPORT = "report";
    private static final String CMD_SCHEDULE = "schedule";

    private KostikOMatic matic;
    private static final String REPORTING_TIMER_NAME = "reportingTimer";
    protected Timer timer = new Timer(REPORTING_TIMER_NAME);
    private static final int REPORT_GENERATION_DELAY = 3600;
    protected TimerTask reportingTask;

    public Console() {
        reportingTask = new TimerTask() {
            @Override
            public void run() {
                produceReport();
            }
        };
    }

    public static void main(String[] args) {
        final Console console = new Console();
        console.init(args);
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
        reportingTask.cancel();
        timer.cancel();
    }


    private boolean processUserInput(String input) {
        if (CMD_EXIT.equalsIgnoreCase(input)) {
            return true;
        } else if (CMD_REPORT.equalsIgnoreCase(input)) {
            produceReport();
        } else if (CMD_SCHEDULE.equalsIgnoreCase(input)) {
            scheduleRecurringReportGeneration();
            System.out.println("Recurring report generation has been scheduled, once in " + 
                    REPORT_GENERATION_DELAY/3600 + " munutes");
        } else if (input.trim().length() > 0){
            System.out.println("Unrecognized command");
        }
        return false;
    }

    private void produceReport() {
        matic.run();
    }

    private void scheduleRecurringReportGeneration() {
        timer.schedule(reportingTask, 500, REPORT_GENERATION_DELAY);
    }

    private void init(String[] args) {
        matic = new KostikOMatic(args[0]);
        matic.init();
        System.out.println("Jira Issue Watcher");
    }

    private void printCommandPromptPrefix() {
        System.out.print(">>> ");
    }
}
