package lv.jake.jiw.application.validation;

import lv.jake.jiw.application.TimeService;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 19, 2010
 * Time: 12:32:00 PM
 */
public abstract class AbstractIssueValidator implements IssueValidator {
    protected TimeService timeService;

    public AbstractIssueValidator(TimeService timeService) {
        this.timeService = timeService;
    }

}
