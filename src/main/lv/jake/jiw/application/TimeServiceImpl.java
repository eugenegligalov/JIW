package lv.jake.jiw.application;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 12, 2010
 * Time: 8:18:24 PM
 */
public class TimeServiceImpl implements TimeService {

    public Calendar getCalendar() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTime(new Date());
        return currentDate;
    }

    public Calendar createCalendarFromDate(Date date) {
        final Calendar instance = GregorianCalendar.getInstance();
        instance.setTime(date);
        return instance;
    }

    public long getTimeDifference(Calendar startDate, Calendar endDate) {
        long milliseconds1 = startDate.getTimeInMillis();
        long milliseconds2 = endDate.getTimeInMillis();
        return milliseconds2 - milliseconds1;
    }

    public long getTimeDifferenceInDays(Calendar startDate, Calendar endDate) {
        return getTimeDifference(startDate, endDate) / (24 * 60 * 60 * 1000);
    }

    public long getTimeDifferenceInHours(Calendar startDate, Calendar endDate) {
        return getTimeDifference(startDate, endDate) / (60 * 60 * 1000);
    }

    public long getTimeDifferenceInMinutes(Calendar startDate, Calendar endDate) {
        return getTimeDifference(startDate, endDate) / (60 * 1000);
    }
}
