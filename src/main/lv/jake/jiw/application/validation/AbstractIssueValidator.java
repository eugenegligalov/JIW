package lv.jake.jiw.application.validation;

import lv.jake.jiw.application.IssueStatus;
import lv.jake.jiw.application.TimeService;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 19, 2010
 * Time: 12:32:00 PM
 */
public abstract class AbstractIssueValidator implements IssueValidator  {
    protected TimeService timeService;

    public AbstractIssueValidator(TimeService timeService) {
        this.timeService = timeService;
    }

}
