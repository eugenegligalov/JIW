import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: Jaker
 * Date: 2010.11.4
 * Time: 01:20:58
 * To change this template use File | Settings | File Templates.
 */
public class DueDateCheckerTest extends TestCase {
    public void testGetDueDateStatus(){
        DueDateChecker dueDateChecker = new DueDateChecker();
        assertEquals(dueDateChecker.getDueDateStatus("2010-03-29 11:23:02.0", null,"1","2010-03-29 11:23:02.0"),-2);
    }
}
