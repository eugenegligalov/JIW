package lv.jake.jiw;

import junit.framework.TestCase;

/**
 * User: Jaker
 * Date: 2010.11.4
 * Time: 01:20:58
 * 2000-12-2 0:40:0;
 */
public class DueDateCheckerTest extends TestCase {
    private MockTimeService timeService;

    public void testGetStatusForBlocker(){
        DueDateChecker dueDateChecker = new DueDateChecker(timeService);
//        assertEquals(dueDateChecker.getDueDateStatus("2000-12-2 00:31:00.0", "2000-12-2 00:31:00.0","1","2000-12-2 00:31:00.0"),DueDateChecker.OK);
        assertEquals(dueDateChecker.getDueDateStatus("2000-12-2 10:28:00.0", null,"1","2000-12-2 10:31:00.0"),DueDateChecker.DUE_DATE_NOT_SET);
        assertEquals(dueDateChecker.getDueDateStatus("2000-12-2 10:35:00.0", null,"1","2000-12-2 10:31:00.0"),DueDateChecker.OK);
        assertEquals(dueDateChecker.getDueDateStatus("2000-12-2 08:40:00.0", "2000-12-2 12:31:00.0","1","2000-12-2 09:38:00.0"),DueDateChecker.NOT_COMMENTED);
        assertEquals(dueDateChecker.getTimeDifferenceInDays(timeService.getCalendar(),timeService.getCalendar()),0);
        assertEquals(dueDateChecker.getTimeDifferenceInHours(timeService.getCalendar(),timeService.getCalendar()),0);
        assertEquals(dueDateChecker.getTimeDifferenceInMinutes(timeService.getCalendar(),timeService.getCalendar()),0);
    }
    public void testGetStatusForCritical(){
        DueDateChecker dueDateChecker = new DueDateChecker(timeService);
    }
    public void testGetStatusForMajor(){
        DueDateChecker dueDateChecker = new DueDateChecker(timeService);
    }
    public void testGetStatusForMinor(){
        DueDateChecker dueDateChecker = new DueDateChecker(timeService);
    }
    public void testGetStatusForTrivial(){
        DueDateChecker dueDateChecker = new DueDateChecker(timeService);
    }

    protected void setUp() throws Exception {
        super.setUp();
        timeService = new MockTimeService();
    }
}
