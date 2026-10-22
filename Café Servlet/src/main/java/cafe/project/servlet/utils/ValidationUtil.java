package cafe.project.servlet.utils;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ValidationUtil {

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    private static final Validator validator = factory.getValidator();

    public static Validator getValidator() {
        return validator;
    }
}