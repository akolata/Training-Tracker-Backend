package pl.akolata.trainingtracker.user.service;

import pl.akolata.trainingtracker.core.dto.OperationResult;
import pl.akolata.trainingtracker.core.entity.User;
import pl.akolata.trainingtracker.user.command.SignUpCommand;

public interface UserSignUpService {
    OperationResult<User> signUp(SignUpCommand command);
}
