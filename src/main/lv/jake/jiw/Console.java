package lv.jake.jiw;

import com.google.inject.Inject;
import lv.jake.jiw.services.IssueReportGenerator;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 13, 2010
 * Time: 12:06:29 PM
 * <p/>
 * Implements console command propmt for user interaction and enables running of background
 * checking processes.
 */
public class Console {
    private static org.apache.log4j.Logger log = Logger.getLogger(Console.class);
    private static final String CMD_EXIT = "exit";
    private static final String CMD_REPORT = "report";
    private static final String CMD_SCHEDULE = "schedule";
    private static final String REPORTING_TIMER_NAME = "reportingTimer";
    private static final int REPORT_GENERATION_DELAY = 3600;

    private IssueReportGenerator reportGenerator;
    protected Timer timer = new Timer(REPORTING_TIMER_NAME);
    protected TimerTask reportingTask;

    @Inject
    public Console(final IssueReportGenerator reportGenerator) {
        reportingTask = new TimerTask() {
            @Override
            public void run() {
                produceReport();
            }
        };
        this.reportGenerator = reportGenerator;
        log.info("Jira Issue Watcher");
    }


    public void startProcessingUserInput() {
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
        if (null == input) {
            // exit on end of stream
            return false;
        }
        if (CMD_EXIT.equalsIgnoreCase(input)) {
            return true;
        } else if (CMD_REPORT.equalsIgnoreCase(input)) {
            produceReport();
        } else if (CMD_SCHEDULE.equalsIgnoreCase(input)) {
            scheduleRecurringReportGeneration();
            log.info("Recurring report generation has been scheduled, once in " +
                    REPORT_GENERATION_DELAY / 3600 + " munutes");
        } else if (input.trim().length() > 0) {
            log.info("Unrecognized command");
        }
        return false;
    }

    private void produceReport() {
        reportGenerator.run();
    }

    private void scheduleRecurringReportGeneration() {
        timer.schedule(reportingTask, 500, REPORT_GENERATION_DELAY);
    }

    private void printCommandPromptPrefix() {
        log.info(">>> ");
    }
}
