package lv.jake.jiw;

import com.google.inject.Inject;
import lv.jake.jiw.application.Configuration;
import lv.jake.jiw.application.IssueReportGenerator;
import lv.jake.jiw.application.JiwServiceException;
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
    private static final org.apache.log4j.Logger log = Logger.getLogger(Console.class);

    private static final String CMD_EXIT = "exit";
    private static final String CMD_REPORT = "report";
    private static final String CMD_SCHEDULE = "schedule";
    private static final String REPORTING_TIMER_NAME = "reportingTimer";

    protected final IssueReportGenerator reportGenerator;
    protected final Timer timer = new Timer(REPORTING_TIMER_NAME);
    protected final TimerTask reportingTask;
    protected final Configuration configuration;

    @Inject
    public Console(final IssueReportGenerator reportGenerator, final Configuration configuration) {
        this.configuration = configuration;
        this.reportGenerator = reportGenerator;

        reportingTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    produceReport();
                } catch (JiwServiceException e) {
                    log.error(e);
                    this.cancel();
                }
            }
        };

        System.out.println("Jira Issue Watcher");
    }


    public void startProcessingUserInput() throws JiwServiceException {
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

    private boolean processUserInput(String input) throws JiwServiceException {
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
            System.out.println("Recurring report generation has been scheduled, once in " +
                    Configuration.DEFAULT_REPORT_GENERATION_DELAY / (60 * 1000) + " minutes");
        } else if (input.trim().length() > 0) {
            System.out.println("Unrecognized command");
        }
        return false;
    }

    private void produceReport() throws JiwServiceException {
        reportGenerator.run();
    }

    private void scheduleRecurringReportGeneration() {
        timer.schedule(reportingTask, 500, configuration.getReportGenerationPeriod());
    }

    private void printCommandPromptPrefix() {
        System.out.print(">>> ");
    }
}
