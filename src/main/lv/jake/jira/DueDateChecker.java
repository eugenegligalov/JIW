package lv.jake.jira;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

public class DueDateChecker {
    private static org.apache.log4j.Logger log = Logger.getLogger(DueDateChecker.class);
    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String NOT_VALID = "not valid";
    public static final String OVERDUE = "overdue";
    public static final String NOT_COMMENTED = "not commented";
    public static final String OK = "ok";
    public static final String DUE_DATE_SOON = "due date soon";
    public static final String DUE_DATE_NOT_SET = "due date is not set";

    public void showIssuesDetail(Object[] filters, Map issues) {
        for (Object filter : filters) {
            Map currentFilter = (Map) filter;
            log.info("\n///////--------------- Filter " + currentFilter.get("name") + " ----------------///////");
            showIssueDetail(issues, (String) currentFilter.get("id"));
        }
    }

    public void showIssueDetail(Map issues, String currentIssues) {
        for (Object currentIssue : (Object[]) issues.get(currentIssues)) {
            Map issue = (Map) currentIssue;
            log.info(" - Key: " + issue.get("key") + " - created: " +
                    issue.get("created") + " - Due date: " + issue.get("duedate") +
                    " - Priority: " +
                    getPriorityById(issue.get("priority").toString()) +
                    " /-/ STATUS: " + getDueDateStatus(issue.get("created").toString(),
                    (String) issue.get("duedate"), issue.get("priority").toString(),
                    issue.get("updated").toString()));
            log.debug("ID: " + issue.get("id") + " - Key: " + issue.get("key") + " - created: " +
                    issue.get("created") + " - updated: " + issue.get("updated") +
                    " - Due date: " + issue.get("duedate") + " - Priority: " +
                    getPriorityById(issue.get("priority").toString()) +
                    " /-/ STATUS: " + getDueDateStatus(issue.get("created").toString(),
                    (String) issue.get("duedate"), issue.get("priority").toString(),
                    issue.get("updated").toString()));
//            log.info("--Summary: " + issue.get("summary"));
//            Object[] comments = null;
//                comments = jira.getComments(rpcclient, loginToken, (String) issue.get("key"));
//                log.info("comments count: " + comments.length);
        }

    }

    public String getPriorityById(String id) {
        if (Integer.valueOf(id) == 1) {
            new Date().getTime();
            return "Blocker";
        }
        if (Integer.valueOf(id) == 2) {
            return "Critical";
        }
        if (Integer.valueOf(id) == 3) {
            return "Major";
        }
        if (Integer.valueOf(id) == 4) {
            return "Minor";
        }
        if (Integer.valueOf(id) == 5) {
            return "Trivial";
        }
        return NOT_VALID;
    }

    public String getDueDateStatus(String created, String duedate, String priority, String updated) {
        Calendar createdDateCalendar = null;
        Calendar dueDateCalendar = null;
        Calendar updatedDateCalendar = null;
        SimpleDateFormat createdDateParser = new SimpleDateFormat(DATE_PATTERN);
        SimpleDateFormat duedateParser = new SimpleDateFormat(DATE_PATTERN);
        SimpleDateFormat updateDateParser = new SimpleDateFormat(DATE_PATTERN);
        try {
            if (duedate != null) {
                duedateParser.parse(duedate);
                dueDateCalendar = duedateParser.getCalendar();
                dueDateCalendar.set(Calendar.HOUR, 18);
                dueDateCalendar.set(Calendar.MINUTE, 0);
            }
            updateDateParser.parse(updated);
            createdDateParser.parse(created);


            createdDateCalendar = createdDateParser.getCalendar();
            updatedDateCalendar = updateDateParser.getCalendar();
        } catch (ParseException e) {
            log.error(e);
        }
        if (Integer.valueOf(priority) == 1) {
            return getStatusForBlocker(dueDateCalendar, updatedDateCalendar, createdDateCalendar);
        }
        if (Integer.valueOf(priority) == 2) {
            return getStatusForCritical(dueDateCalendar, updatedDateCalendar, createdDateCalendar);
        }
        if (Integer.valueOf(priority) == 3) {
            return getStatusForMajor(dueDateCalendar, updatedDateCalendar, createdDateCalendar);
        }
        if (Integer.valueOf(priority) == 4) {
            return getStatusForMinor(dueDateCalendar, updatedDateCalendar, createdDateCalendar);
        }
        if (Integer.valueOf(priority) == 5) {
            return getStatusForTrivial(dueDateCalendar, updatedDateCalendar, createdDateCalendar);
        }
        return NOT_VALID;
    }

    public String getStatusForBlocker(Calendar duedate, Calendar updated, Calendar created) {
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTime(new Date());

        if (duedate == null && getTimeDifferenceInMinutes(updated, currentDate) > 10) {
            return DUE_DATE_NOT_SET;
        }

        if (getTimeDifferenceInMinutes(updated, currentDate) > 58) {
            return NOT_COMMENTED;
        }

        if (getTimeDifferenceInHours(created, currentDate) > 4) {
            return OVERDUE;
        }
        return OK;
    }

    public String getStatusForCritical(Calendar duedate, Calendar updated, Calendar created) {
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTime(new Date());

        if (duedate == null && getTimeDifferenceInMinutes(updated, currentDate) > 30) {
            return DUE_DATE_NOT_SET;
        }

        if (getTimeDifferenceInHours(updated, currentDate) > 4) {
            return NOT_COMMENTED;
        }

        if (getTimeDifferenceInDays(created, currentDate) > 2) {
            return OVERDUE;
        }
        return OK;
    }

    public String getStatusForMajor(Calendar duedate, Calendar updated, Calendar created) {
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTime(new Date());
        if (duedate != null && getTimeDifferenceInHours(currentDate, duedate) < 24 && getTimeDifferenceInHours(currentDate, duedate) > 0) {
            return DUE_DATE_SOON;
        }
        if (duedate != null && getTimeDifferenceInHours(currentDate, duedate) <= 0) {
            return OVERDUE;
        }
        return OK;
    }

    public String getStatusForMinor(Calendar duedate, Calendar updated, Calendar created) {
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTime(new Date());
        if (duedate != null && getTimeDifferenceInHours(currentDate, duedate) < 24 && getTimeDifferenceInHours(currentDate, duedate) > 0) {
            return DUE_DATE_SOON;
        }
        if (duedate != null && getTimeDifferenceInHours(currentDate, duedate) <= 0) {
            return OVERDUE;
        }
        return OK;
    }

    public String getStatusForTrivial(Calendar duedate, Calendar updated, Calendar created) {
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTime(new Date());
        if (duedate != null && getTimeDifferenceInHours(currentDate, duedate) < 24 && getTimeDifferenceInHours(currentDate, duedate) > 0) {
            return DUE_DATE_SOON;
        }
        if (duedate != null && getTimeDifferenceInHours(currentDate, duedate) <= 0) {
            return OVERDUE;
        }
        return OK;
    }

    public long getTimeDifferenceInMinutes(Calendar startDate, Calendar endDate) {
        long milliseconds1 = startDate.getTimeInMillis();
        long milliseconds2 = endDate.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;
        return diff / (60 * 1000);
    }

    public long getTimeDifferenceInHours(Calendar startDate, Calendar endDate) {
        long milliseconds1 = startDate.getTimeInMillis();
        long milliseconds2 = endDate.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;
        return diff / (60 * 60 * 1000);
    }

    public long getTimeDifferenceInDays(Calendar startDate, Calendar endDate) {
        long milliseconds1 = startDate.getTimeInMillis();
        long milliseconds2 = endDate.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;
        return diff / (24 * 60 * 60 * 1000);
    }
}
