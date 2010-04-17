package lv.jake.jiw.application;

import com.google.inject.Inject;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DueDateChecker {
    private static org.apache.log4j.Logger log = Logger.getLogger(DueDateChecker.class);

    protected final TimeService timeService;

    @Inject
    public DueDateChecker(TimeService timeService) {
        this.timeService = timeService;
    }

    public IssueStatus getDueDateStatus(Date created, Date duedate, String priority, Date updated) {
        Calendar createdDateCalendar = createCalendarFromDate(created);
        Calendar updatedDateCalendar = createCalendarFromDate(updated);
        Calendar dueDateCalendar = null;
        if (duedate != null) {
            dueDateCalendar = createCalendarFromDate(duedate);
            dueDateCalendar.set(Calendar.HOUR, 18);
            dueDateCalendar.set(Calendar.MINUTE, 0);
        }

        if (Integer.valueOf(priority) == 1) {
            return getStatusForBlocker(dueDateCalendar, updatedDateCalendar, createdDateCalendar);
        }
        if (Integer.valueOf(priority) == 2) {
            return getStatusForCritical(dueDateCalendar, updatedDateCalendar, createdDateCalendar);
        }
        if (Integer.valueOf(priority) == 3) {
            return getStatusForMajor(dueDateCalendar, updatedDateCalendar, createdDateCalendar);
        }
        if (Integer.valueOf(priority) == 4) {
            return getStatusForMinor(dueDateCalendar, updatedDateCalendar, createdDateCalendar);
        }
        if (Integer.valueOf(priority) == 5) {
            return getStatusForTrivial(dueDateCalendar, updatedDateCalendar, createdDateCalendar);
        }
        return null;
    }

    private Calendar createCalendarFromDate(Date created) {
        Calendar createdDateCalendar;
        createdDateCalendar = GregorianCalendar.getInstance();
        createdDateCalendar.setTime(created);
        return createdDateCalendar;
    }

    public IssueStatus getStatusForBlocker(Calendar duedate, Calendar updated, Calendar created) {
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

    public IssueStatus getStatusForCritical(Calendar duedate, Calendar updated, Calendar created) {
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

    public IssueStatus getStatusForMajor(Calendar duedate, Calendar updated, Calendar created) {
        IssueStatus status = new IssueStatus();
        Calendar currentDate = timeService.getCalendar();
        if (duedate == null && getTimeDifferenceInHours(created, currentDate) > 24) {
            status.setDueDateNotSet(true);
        }
        if (duedate != null && getTimeDifferenceInHours(currentDate, duedate) < 24 && getTimeDifferenceInHours(currentDate, duedate) > 0) {
            status.setDueDateSoon(true);
        }
        if (duedate != null && getTimeDifferenceInHours(currentDate, duedate) <= 0) {
            status.setOverdue(true);
        }
        return status;
    }

    public IssueStatus getStatusForMinor(Calendar duedate, Calendar updated, Calendar created) {
        IssueStatus status = new IssueStatus();
        Calendar currentDate = timeService.getCalendar();
        if (duedate != null && getTimeDifferenceInHours(currentDate, duedate) < 24 && getTimeDifferenceInHours(currentDate, duedate) > 0) {
            status.setDueDateSoon(true);
        }
        if (duedate != null && getTimeDifferenceInHours(currentDate, duedate) <= 0) {
            status.setOverdue(true);
        }
        return status;
    }

    public IssueStatus getStatusForTrivial(Calendar duedate, Calendar updated, Calendar created) {
        IssueStatus status = new IssueStatus();
        Calendar currentDate = timeService.getCalendar();
        if (duedate != null && getTimeDifferenceInHours(currentDate, duedate) < 24 && getTimeDifferenceInHours(currentDate, duedate) > 0) {
            status.setDueDateSoon(true);
        }
        if (duedate != null && getTimeDifferenceInHours(currentDate, duedate) <= 0) {
            status.setOverdue(true);
        }
        return status;
    }

    public long getTimeDifferenceInMinutes(Calendar startDate, Calendar endDate) {
        long milliseconds1 = startDate.getTimeInMillis();
        long milliseconds2 = endDate.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;
        return diff / (60 * 1000);
    }

    public long getTimeDifferenceInHours(Calendar startDate, Calendar endDate) {
        long milliseconds1 = startDate.getTimeInMillis();
        long milliseconds2 = endDate.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;
        return diff / (60 * 60 * 1000);
    }

    public long getTimeDifferenceInDays(Calendar startDate, Calendar endDate) {
        long milliseconds1 = startDate.getTimeInMillis();
        long milliseconds2 = endDate.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;
        return diff / (24 * 60 * 60 * 1000);
    }
}
