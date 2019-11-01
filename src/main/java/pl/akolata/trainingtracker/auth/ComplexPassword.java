package pl.akolata.trainingtracker.auth;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ComplexPasswordValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@interface ComplexPassword {
    String message() default "Password is not complex enough";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
