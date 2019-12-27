package pl.akolata.trainingtracker.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RoleName {
    ROLE_ADMIN(Annotation.ROLE_ADMIN), ROLE_USER(Annotation.ROLE_USER);

    @Getter
    private final String roleName;

    public static class Annotation {
        public static final String ROLE_ADMIN = "ROLE_ADMIN";
        public static final String ROLE_USER = "ROLE_USER";
    }
}
