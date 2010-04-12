package lv.jake.jira;

import junit.framework.TestCase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Jaker
 * Date: 2010.11.4
 * Time: 01:20:58
 */
public class DueDateCheckerTest extends TestCase {
    private MockTimeService timeService;

    public void testGetStatusForBlocker(){
        SimpleDateFormat sdf = new SimpleDateFormat(DueDateChecker.DATE_PATTERN);
        sdf.format(new Date());
        DueDateChecker dueDateChecker = new DueDateChecker(timeService);
        assertEquals(dueDateChecker.getDueDateStatus("2010-03-29 11:23:02.0", "2010-03-29 11:23:02.0","1","2010-03-29 11:23:02.0"),DueDateChecker.OVERDUE);
    }
    public void testGetStatusForCritical(){
        DueDateChecker dueDateChecker = new DueDateChecker(timeService);
//        assertEquals(dueDateChecker.getDueDateStatus("2010-03-29 11:23:02.0", "2010-03-29 11:23:02.0","1","2010-03-29 11:23:02.0"),lv.jake.jira.DueDateChecker.OVERDUE);
    }
    public void testGetStatusForMajor(){
        DueDateChecker dueDateChecker = new DueDateChecker(timeService);
//        assertEquals(dueDateChecker.getDueDateStatus("2010-03-29 11:23:02.0", "2010-03-29 11:23:02.0","1","2010-03-29 11:23:02.0"),lv.jake.jira.DueDateChecker.OVERDUE);
    }
    public void testGetStatusForMinor(){
        DueDateChecker dueDateChecker = new DueDateChecker(timeService);
//        assertEquals(dueDateChecker.getDueDateStatus("2010-03-29 11:23:02.0", "2010-03-29 11:23:02.0","1","2010-03-29 11:23:02.0"),lv.jake.jira.DueDateChecker.OVERDUE);
    }
    public void testGetStatusForTrivial(){
        DueDateChecker dueDateChecker = new DueDateChecker(timeService);
//        assertEquals(dueDateChecker.getDueDateStatus("2010-03-29 11:23:02.0", "2010-03-29 11:23:02.0","1","2010-03-29 11:23:02.0"),lv.jake.jira.DueDateChecker.OVERDUE);
    }

    protected void setUp() throws Exception {
        super.setUp();
        timeService = new MockTimeService();
    }
}
