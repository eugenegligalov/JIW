package lv.jake.jiw.application.validation;

import lv.jake.jiw.application.IssueStatus;
import lv.jake.jiw.application.TimeService;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 19, 2010
 * Time: 12:32:00 PM
 */
public abstract class AbstractIssueValidator implements IssueValidator  {
    protected TimeService timeService;

    public AbstractIssueValidator(TimeService timeService) {
        this.timeService = timeService;
    }

    public static Calendar createCalendarFromDate(Date date) {
        final Calendar instance = GregorianCalendar.getInstance();
        instance.setTime(date);
        return instance;
    }

    public IssueStatus getStatusForBlocker(Calendar duedate, Calendar updated, Calendar created) {
        IssueStatus status = new IssueStatus();
        Calendar currentDate = timeService.getCalendar();

        if (duedate == null && getTimeDifferenceInMinutes(created, currentDate) > 10) {
            status.setDueDateNotSet(true);
        }

        if (getTimeDifferenceInMinutes(updated, currentDate) > 58) {
            status.setNotCommented(true);
        }

        if (duedate != null && getTimeDifferenceInHours(currentDate, duedate) < 24 && getTimeDifferenceInHours(currentDate, duedate) > 0) {
            status.setDueDateSoon(true);
        }

        if (duedate != null && getTimeDifferenceInHours(currentDate, duedate) <= 0) {
            status.setOverdue(true);
        }

        if (getTimeDifferenceInHours(created, currentDate) > 4) {
            status.setSlaOverdue(true);
        }
        return status;
    }

    public static long getTimeDifferenceInMinutes(Calendar startDate, Calendar endDate) {
        long milliseconds1 = startDate.getTimeInMillis();
        long milliseconds2 = endDate.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;
        return diff / (60 * 1000);
    }

    public static long getTimeDifferenceInHours(Calendar startDate, Calendar endDate) {
        long milliseconds1 = startDate.getTimeInMillis();
        long milliseconds2 = endDate.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;
        return diff / (60 * 60 * 1000);
    }

    public static long getTimeDifferenceInDays(Calendar startDate, Calendar endDate) {
        long milliseconds1 = startDate.getTimeInMillis();
        long milliseconds2 = endDate.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;
        return diff / (24 * 60 * 60 * 1000);
    }
}
