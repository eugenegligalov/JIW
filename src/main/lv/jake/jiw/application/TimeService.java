package lv.jake.jiw.application;

import java.util.Calendar;
import java.util.Date;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 12, 2010
 * Time: 8:16:51 PM
 */
public interface TimeService {

    Calendar getCalendar();

    Calendar createCalendarFromDate(Date date);

    long getTimeDifference(Calendar startDate, Calendar endDate);

    long getTimeDifferenceInDays(Calendar startDate, Calendar endDate);

    long getTimeDifferenceInHours(Calendar startDate, Calendar endDate);

    long getTimeDifferenceInMinutes(Calendar startDate, Calendar endDate);

}
