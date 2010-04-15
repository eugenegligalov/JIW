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
    public Calendar getCalendar() {
        return new GregorianCalendar(2000, 12-1, 2, 10, 40, 0 );
    }
}
