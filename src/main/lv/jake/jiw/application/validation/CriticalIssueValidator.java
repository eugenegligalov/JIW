package lv.jake.jiw.application.validation;

import lv.jake.jiw.application.IssueStatus;
import lv.jake.jiw.application.TimeService;
import lv.jake.jiw.domain.JiraIssue;

import java.util.Calendar;
import java.util.Date;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 19, 2010
 * Time: 12:46:19 PM
 */
public class CriticalIssueValidator extends AbstractIssueValidator {

    public CriticalIssueValidator(TimeService timeService) {
        super(timeService);
    }

    public boolean accepts(JiraIssue issue) {
        return Integer.valueOf(issue.getPriority()) == 2;
    }

    public IssueStatus validate(JiraIssue issue) {
        return validateCritical(issue);
    }

    protected IssueStatus validateCritical(JiraIssue issue) {
        Calendar createdDateCalendar = createCalendarFromDate(issue.getCreatedDate());
        Calendar updatedDateCalendar = createCalendarFromDate(issue.getLastUpdateDate());
        Calendar dueDateCalendar = null;
        Date duedate = issue.getDueDate();
        if (duedate != null) {
            dueDateCalendar = createCalendarFromDate(duedate);
            dueDateCalendar.set(Calendar.HOUR, 18);
            dueDateCalendar.set(Calendar.MINUTE, 0);
        }
        return getStatusForCritical(dueDateCalendar, updatedDateCalendar, createdDateCalendar);
    }

    protected IssueStatus getStatusForCritical(Calendar duedate, Calendar updated, Calendar created) {
        IssueStatus status = new IssueStatus();
        Calendar currentDate = timeService.getCalendar();

        if (duedate == null && getTimeDifferenceInMinutes(updated, currentDate) > 30) {
            status.setDueDateNotSet(true);
        }

        if (getTimeDifferenceInHours(updated, currentDate) > 4) {
            status.setNotCommented(true);
        }

        if (duedate != null && getTimeDifferenceInHours(currentDate, duedate) < 24 && getTimeDifferenceInHours(currentDate, duedate) > 0) {
            status.setDueDateSoon(true);
        }

        if (duedate != null && getTimeDifferenceInMinutes(currentDate, duedate) <= 0) {
            status.setOverdue(true);
        }

        if (getTimeDifferenceInDays(created, currentDate) > 2) {
            status.setSlaOverdue(true);
        }
        return status;
    }
}
