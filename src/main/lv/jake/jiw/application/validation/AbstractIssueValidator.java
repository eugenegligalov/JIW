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

    public static long getTimeDifferenceInMinutes(Calendar startDate, Calendar endDate) {
        return getTimeDifference(startDate, endDate) / (60 * 1000);
    }

    public static long getTimeDifferenceInHours(Calendar startDate, Calendar endDate) {
        return getTimeDifference(startDate, endDate) / (60 * 60 * 1000);
    }

    public static long getTimeDifferenceInDays(Calendar startDate, Calendar endDate) {
        return getTimeDifference(startDate, endDate) / (24 * 60 * 60 * 1000);
    }

    private static long getTimeDifference(Calendar startDate, Calendar endDate) {
        long milliseconds1 = startDate.getTimeInMillis();
        long milliseconds2 = endDate.getTimeInMillis();
        return milliseconds2 - milliseconds1;
    }
}
