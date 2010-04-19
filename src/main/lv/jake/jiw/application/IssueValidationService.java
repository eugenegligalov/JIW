package lv.jake.jiw.application;

import com.google.inject.Inject;
import lv.jake.jiw.application.validation.*;
import lv.jake.jiw.domain.JiraIssue;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.List;

public class IssueValidationService {
    private static org.apache.log4j.Logger log = Logger.getLogger(IssueValidationService.class);

    protected final TimeService timeService;
    protected final Collection<IssueValidator> validators;

    @Inject
    public IssueValidationService(TimeService timeService, IssueValidatorRepository repository) {
        this.timeService = timeService;
        this.validators = repository.getValidators();
    }

    public IssueStatus validateIssue(JiraIssue issue) {
        for (IssueValidator validator : validators) {
            if (validator.accepts(issue)) {
                return validator.validate(issue);
            }
        }
        return IssueStatus.STATUS_NOT_VALID;
    }

}
