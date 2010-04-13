package lv.jake.jiw;

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
}
