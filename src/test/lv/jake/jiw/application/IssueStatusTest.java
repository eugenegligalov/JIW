package lv.jake.jiw.application;

import junit.framework.TestCase;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 18, 2010
 * Time: 2:09:39 AM
 */
public class IssueStatusTest extends TestCase {
    public void testIsOk() {
        final IssueStatus status1 = new IssueStatus();
        assertTrue(status1.isOk());
        status1.setOverdue(true);
        assertFalse(status1.isOk());
        status1.setOverdue(false);
        status1.setDueDateNotSet(true);
        status1.setSlaSoon(true);
        assertFalse(status1.isOk());
    }
}
