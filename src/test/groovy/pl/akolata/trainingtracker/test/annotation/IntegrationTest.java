package pl.akolata.trainingtracker.test.annotation;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Inherited
@SpringBootTest
@WithMockUser(
        username = "TT_TEST_USER",
        roles = {"USER"}
)
public @interface IntegrationTest {
}
