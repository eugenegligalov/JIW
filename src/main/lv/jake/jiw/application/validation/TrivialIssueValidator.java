package lv.jake.jiw.application.validation;

import lv.jake.jiw.application.IssueStatus;
import lv.jake.jiw.application.TimeService;
import lv.jake.jiw.domain.JiraIssue;

import java.util.Calendar;
import java.util.Date;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 19, 2010
 * Time: 12:35:26 PM
 */
public class TrivialIssueValidator extends AbstractIssueValidator {

    public TrivialIssueValidator(TimeService timeService) {
        super(timeService);
    }

    public boolean accepts(JiraIssue issue) {
        return Integer.valueOf(issue.getPriority()) == 5;
    }

    public IssueStatus validate(JiraIssue issue) {
        return validateTrivial(timeService, issue);
    }

    public IssueStatus validateTrivial(TimeService timeService, JiraIssue issue) {
        Calendar createdDateCalendar = timeService.createCalendarFromDate(issue.getCreatedDate());
        Calendar updatedDateCalendar = timeService.createCalendarFromDate(issue.getLastUpdateDate());
        Calendar dueDateCalendar = null;
        Date duedate = issue.getDueDate();
        if (duedate != null) {
            dueDateCalendar = timeService.createCalendarFromDate(duedate);
            dueDateCalendar.set(Calendar.HOUR, 18);
            dueDateCalendar.set(Calendar.MINUTE, 0);
        }
        return getStatusForTrivial(timeService, dueDateCalendar, updatedDateCalendar, createdDateCalendar);
    }

    public IssueStatus getStatusForTrivial(TimeService timeService, Calendar duedate, Calendar updated, Calendar created) {
        IssueStatus status = new IssueStatus();
        Calendar currentDate = timeService.getCalendar();
        if (duedate != null && timeService.getTimeDifferenceInHours(currentDate, duedate) < 24 && timeService.getTimeDifferenceInHours(currentDate, duedate) > 0) {
            status.setDueDateSoon(true);
        }
        if (duedate != null && timeService.getTimeDifferenceInHours(currentDate, duedate) <= 0) {
            status.setOverdue(true);
        }
        return status;
    }
}
