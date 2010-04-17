package lv.jake.jiw.application;

import junit.framework.TestCase;
import lv.jake.jiw.application.DueDateChecker;

import java.util.GregorianCalendar;

/**
 * User: Jaker
 * Date: 2010.11.4
 * Time: 01:20:58
 * 2000-12-2 0:40:0;
 */
public class DueDateCheckerTest extends TestCase {
    private MockTimeService timeService;

    public void testGetStatusForBlocker() {
        DueDateChecker dueDateChecker = new DueDateChecker(timeService);

        assertEquals(true,
                dueDateChecker.getDueDateStatus(
                        new GregorianCalendar(2000, 11, 2, 10, 28, 0).getTime(),
                        null, "1",
                        new GregorianCalendar(2000, 11, 2, 10, 31, 0).getTime()
                ).isDueDateNotSet()
        );
        assertEquals(true,
                dueDateChecker.getDueDateStatus(
                        new GregorianCalendar(2000, 11, 2, 10, 35, 0).getTime(),
                        null, "1",
                        new GregorianCalendar(2000, 11, 2, 10, 31, 0).getTime()
                ).isOk()
        );
        assertEquals(true,
                dueDateChecker.getDueDateStatus(
                        new GregorianCalendar(2000, 11, 2, 8, 40, 0).getTime(),
                        new GregorianCalendar(2000, 11, 2, 12, 31, 0).getTime(),
                        "1",
                        new GregorianCalendar(2000, 11, 2, 9, 38, 0).getTime()
                ).isNotCommented()
        );

        assertEquals(true,
                dueDateChecker.getDueDateStatus(
                        new GregorianCalendar(2000, 11, 1, 8, 40, 0).getTime(),
                        new GregorianCalendar(2000, 11, 1, 12, 31, 0).getTime(),
                        "2",
                        new GregorianCalendar(2000, 11, 1, 9, 38, 0).getTime()
                ).isOverdue()
        );

        assertEquals(dueDateChecker.getTimeDifferenceInDays(timeService.getCalendar(), timeService.getCalendar()), 0);
        assertEquals(dueDateChecker.getTimeDifferenceInHours(timeService.getCalendar(), timeService.getCalendar()), 0);
        assertEquals(dueDateChecker.getTimeDifferenceInMinutes(timeService.getCalendar(), timeService.getCalendar()), 0);
    }

    public void testGetStatusForCritical() {
        DueDateChecker dueDateChecker = new DueDateChecker(timeService);
    }

    public void testGetStatusForMajor() {
        DueDateChecker dueDateChecker = new DueDateChecker(timeService);
    }

    public void testGetStatusForMinor() {
        DueDateChecker dueDateChecker = new DueDateChecker(timeService);
    }

    public void testGetStatusForTrivial() {
        DueDateChecker dueDateChecker = new DueDateChecker(timeService);
    }

    protected void setUp() throws Exception {
        super.setUp();
        timeService = new MockTimeService();
    }
}
