package lv.jake.jiw;

import lv.jake.jiw.services.TimeService;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
* Author: Konstantin Zmanovsky
* Date: Apr 12, 2010
* Time: 8:27:10 PM
*/
class MockTimeService implements TimeService {
    protected GregorianCalendar calendar;

    public MockTimeService() {
        calendar = new GregorianCalendar(2000, 12-1, 2, 10, 40, 0);
    }

    public Calendar getCalendar() {
        return calendar;
    }
}
