package lv.jake.jiw.application;

/**
 * User: Eugene Gligalov
 * Date: 2010.17.4
 * Time: 19:22:33
 */
public class IssueStatus {
    public static final String NOT_VALID = "not valid";
    public static final String OVERDUE = "overdue";
    public static final String SLA_OVERDUE = "sla overdue";
    public static final String NOT_COMMENTED = "not commented";
    public static final String DUE_DATE_SOON = "due date soon";
    public static final String SLA_SOON = "sla soon";
    public static final String DUE_DATE_NOT_SET = "due date is not set";

    public static final IssueStatus STATUS_NOT_VALID = new NotValidIssueStatus();

    protected boolean slaSoon = false;
    protected boolean slaOverdue = false;
    protected boolean notCommented = false;
    protected boolean dueDateNotSet = false;
    protected boolean dueDateSoon = false;
    protected boolean overdue = false;
    protected boolean notValid = false;

    public boolean isOk() {
        return !(slaSoon || slaOverdue || notCommented || dueDateNotSet || dueDateSoon || overdue || notValid);
    }

    public boolean isSlaSoon() {
        return slaSoon;
    }

    public boolean isSlaOverdue() {
        return slaOverdue;
    }

    public boolean isNotCommented() {
        return notCommented;
    }

    public boolean isDueDateNotSet() {
        return dueDateNotSet;
    }

    public boolean isDueDateSoon() {
        return dueDateSoon;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public boolean isNotValid() {
        return notValid;
    }

    public void setSlaSoon(boolean slaSoon) {
        this.slaSoon = slaSoon;
    }

    public void setSlaOverdue(boolean slaOverdue) {
        this.slaOverdue = slaOverdue;
    }

    public void setNotCommented(boolean notCommented) {
        this.notCommented = notCommented;
    }

    public void setDueDateNotSet(boolean dueDateNotSet) {
        this.dueDateNotSet = dueDateNotSet;
    }

    public void setDueDateSoon(boolean dueDateSoon) {
        this.dueDateSoon = dueDateSoon;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    public void setNotValid(boolean notValid) {
        this.notValid = notValid;
    }


    public String toString() {
        String stringStatus = "";
        if (slaSoon) stringStatus = stringStatus.concat("(" + SLA_SOON + ")");
        if (slaOverdue) stringStatus = stringStatus.concat("(" + SLA_OVERDUE + ")");
        if (notCommented) stringStatus = stringStatus.concat("(" + NOT_COMMENTED + ")");
        if (overdue) stringStatus = stringStatus.concat("(" + OVERDUE + ")");
        if (dueDateNotSet) stringStatus = stringStatus.concat("(" + DUE_DATE_NOT_SET + ")");
        if (dueDateSoon) stringStatus = stringStatus.concat("(" + DUE_DATE_SOON + ")");
        if (notValid) stringStatus = stringStatus.concat("(" + NOT_VALID + ")");
        return stringStatus;
    }

    public static class NotValidIssueStatus extends IssueStatus {
        private NotValidIssueStatus() {
            setNotValid(true);
        }
    }
}
