package lv.jake.jiw.application;

import junit.framework.TestCase;
import lv.jake.jiw.application.validation.IssueValidatorRepositoryImpl;
import lv.jake.jiw.domain.JiraIssue;

import java.util.GregorianCalendar;

/**
 * User: Jaker
 * Date: 2010.11.4
 * Time: 01:20:58
 * 2000-12-2 0:40:0;
 */
public class IssueValidationServiceTest extends TestCase {
    private MockTimeService timeService;

    public void testValidate() {
        IssueValidationService issueValidationService =
                new IssueValidationService(timeService, new IssueValidatorRepositoryImpl(timeService));

        JiraIssue issue = new JiraIssue();
        issue.setCreatedDate(new GregorianCalendar(2000, 11, 2, 10, 28, 0).getTime());
        issue.setPriority("1");
        issue.setLastUpdateDate(new GregorianCalendar(2000, 11, 2, 10, 31, 0).getTime());

        assertEquals(true, issueValidationService.validateIssue(issue).isDueDateNotSet());

        issue.setCreatedDate(new GregorianCalendar(2000, 11, 2, 10, 35, 0).getTime());
        issue.setLastUpdateDate(new GregorianCalendar(2000, 11, 2, 10, 31, 0).getTime());
        assertEquals(true, issueValidationService.validateIssue(issue).isOk());

        issue.setCreatedDate(new GregorianCalendar(2000, 11, 2, 8, 40, 0).getTime());
        issue.setDueDate(new GregorianCalendar(2000, 11, 2, 12, 31, 0).getTime());
        issue.setLastUpdateDate(new GregorianCalendar(2000, 11, 2, 9, 38, 0).getTime());
        assertEquals(true, issueValidationService.validateIssue(issue).isNotCommented());

        issue.setCreatedDate(new GregorianCalendar(2000, 11, 1, 8, 40, 0).getTime());
        issue.setDueDate(new GregorianCalendar(2000, 11, 1, 12, 31, 0).getTime());
        issue.setPriority("2");
        issue.setLastUpdateDate(new GregorianCalendar(2000, 11, 1, 9, 38, 0).getTime());

        assertEquals(true, issueValidationService.validateIssue(issue).isOverdue());

        assertEquals(timeService.getTimeDifferenceInDays(timeService.getCalendar(), timeService.getCalendar()), 0);
        assertEquals(timeService.getTimeDifferenceInHours(timeService.getCalendar(), timeService.getCalendar()), 0);
        assertEquals(timeService.getTimeDifferenceInMinutes(timeService.getCalendar(), timeService.getCalendar()), 0);
    }

    protected void setUp() throws Exception {
        super.setUp();
        timeService = new MockTimeService();
    }
}
