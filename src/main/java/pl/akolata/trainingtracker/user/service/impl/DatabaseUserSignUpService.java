package pl.akolata.trainingtracker.user.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.akolata.trainingtracker.core.api.ServerErrorException;
import pl.akolata.trainingtracker.core.dto.OperationResult;
import pl.akolata.trainingtracker.core.dto.ValidationResult;
import pl.akolata.trainingtracker.core.entity.Role;
import pl.akolata.trainingtracker.core.entity.RoleName;
import pl.akolata.trainingtracker.core.entity.User;
import pl.akolata.trainingtracker.user.command.SignUpCommand;
import pl.akolata.trainingtracker.user.service.RoleRepository;
import pl.akolata.trainingtracker.user.service.SignUpValidationService;
import pl.akolata.trainingtracker.user.service.UserRepository;
import pl.akolata.trainingtracker.user.service.UserSignUpService;

import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DatabaseUserSignUpService implements UserSignUpService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final SignUpValidationService signUpValidationService;

    @Transactional
    @Override
    public OperationResult<User> signUp(SignUpCommand command) {
        ValidationResult validationResult = signUpValidationService.validate(command);

        if (validationResult.notValid()) {
            return OperationResult.failure(validationResult);
        }

        User user = createUserFromCommand(command);
        return OperationResult.success(userRepository.saveAndFlush(user));
    }

    private User createUserFromCommand(SignUpCommand command) {
        User user = User.createNewActiveUser(
                command.getFirstName(),
                command.getLastName(),
                command.getUsername(),
                command.getEmail(),
                passwordEncoder.encode(command.getRawPassword())
        );

        Optional<Role> userRole = roleRepository.findByName(RoleName.ROLE_USER);
        if (userRole.isEmpty()) {
            throw new ServerErrorException(String.format("It's not possible to create an account for new user, because role [%s] hasn't been found", RoleName.ROLE_USER));
        }

        user.setRoles(Collections.singleton(userRole.get()));
        return user;
    }
}
