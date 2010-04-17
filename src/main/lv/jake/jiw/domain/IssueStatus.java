package lv.jake.jiw.domain;

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
    public static final String OK = "ok";
    public static final String DUE_DATE_SOON = "due date soon";
    public static final String SLA_SOON = "sla soon";
    public static final String DUE_DATE_NOT_SET = "due date is not set";

    protected boolean ok = false;
    protected boolean slaSoon = false;
    protected boolean slaOverdue = false;
    protected boolean notCommented = false;
    protected boolean dueDateNotSet = false;
    protected boolean dueDateSoon = false;
    protected boolean overdue = false;
    protected boolean notValid = false;

    public boolean isOk() {
        return ok;
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

    public void setOk(boolean ok) {
        this.ok = ok;
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
        if (ok) stringStatus = stringStatus.concat(OK + "\n");
        if (slaSoon) stringStatus = stringStatus.concat(SLA_SOON + "\n");
        if (slaOverdue) stringStatus = stringStatus.concat(SLA_OVERDUE + "\n");
        if (notCommented) stringStatus = stringStatus.concat(NOT_COMMENTED + "\n");
        if (overdue) stringStatus = stringStatus.concat(OVERDUE + "\n");
        if (dueDateNotSet) stringStatus = stringStatus.concat(DUE_DATE_NOT_SET + "\n");
        if (dueDateSoon) stringStatus = stringStatus.concat(DUE_DATE_SOON + "\n");
        if (notValid) stringStatus = stringStatus.concat(NOT_VALID + "\n");
        return stringStatus;
    }
}
