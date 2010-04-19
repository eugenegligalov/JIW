package lv.jake.jiw.application.validation;

import lv.jake.jiw.application.IssueStatus;
import lv.jake.jiw.application.TimeService;
import lv.jake.jiw.domain.JiraIssue;

import java.util.Calendar;
import java.util.Date;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 19, 2010
 * Time: 12:19:49 PM
 */
public class BlockerIssueValidator extends AbstractIssueValidator {

    public BlockerIssueValidator(TimeService timeService) {
        super(timeService);
    }

    public boolean accepts(JiraIssue issue) {
        return Integer.valueOf(issue.getPriority()) == 1;
    }

    public IssueStatus validate(JiraIssue issue) {
        Calendar createdDateCalendar = createCalendarFromDate(issue.getCreatedDate());
        Calendar updatedDateCalendar = createCalendarFromDate(issue.getLastUpdateDate());
        Date duedate = issue.getDueDate();
        Calendar dueDateCalendar = null;
        if (duedate != null) {
            dueDateCalendar = createCalendarFromDate(duedate);
            dueDateCalendar.set(Calendar.HOUR, 18);
            dueDateCalendar.set(Calendar.MINUTE, 0);
        }
        return getStatusForBlocker(timeService, dueDateCalendar, updatedDateCalendar, createdDateCalendar);
    }

    public IssueStatus getStatusForBlocker(TimeService timeService, Calendar duedate, Calendar updated, Calendar created) {
        IssueStatus status = new IssueStatus();
        Calendar currentDate = timeService.getCalendar();

        if (duedate == null && getTimeDifferenceInMinutes(created, currentDate) > 10) {
            status.setDueDateNotSet(true);
        }

        if (getTimeDifferenceInMinutes(updated, currentDate) > 58) {
            status.setNotCommented(true);
        }

        if (duedate != null && getTimeDifferenceInHours(currentDate, duedate) < 24 && getTimeDifferenceInHours(currentDate, duedate) > 0) {
            status.setDueDateSoon(true);
        }

        if (duedate != null && getTimeDifferenceInHours(currentDate, duedate) <= 0) {
            status.setOverdue(true);
        }

        if (getTimeDifferenceInHours(created, currentDate) > 4) {
            status.setSlaOverdue(true);
        }
        return status;
    }
}
