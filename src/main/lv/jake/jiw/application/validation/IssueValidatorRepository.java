package lv.jake.jiw.application.validation;

import java.util.Collection;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 19, 2010
 * Time: 1:26:12 PM
 */
public interface IssueValidatorRepository {
    Collection<IssueValidator> getValidators();
}
