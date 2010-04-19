package lv.jake.jiw.application.validation;

import com.google.inject.Inject;
import lv.jake.jiw.application.TimeService;

import java.util.*;

/**
 * Author: Konstantin Zmanovsky
 * Date: Apr 19, 2010
 * Time: 1:21:10 PM
 */
public class IssueValidatorRepositoryImpl implements IssueValidatorRepository {
    protected final List<IssueValidator> validators = new LinkedList<IssueValidator>();
    private final TimeService timeService;

    @Inject
    public IssueValidatorRepositoryImpl(final TimeService timeService) {
        this.timeService = timeService;
        initDefaultValidators();
    }

    public void initDefaultValidators() {
        validators.add(new BlockerIssueValidator(timeService));
        validators.add(new CriticalIssueValidator(timeService));
        validators.add(new MajorIssueValidator(timeService));
        validators.add(new MinorIssueValidator(timeService));
        validators.add(new TrivialIssueValidator(timeService));

    }

    public Collection<IssueValidator> getValidators() {
        return Collections.unmodifiableCollection(validators);
    }
}
