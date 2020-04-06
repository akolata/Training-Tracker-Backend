package pl.akolata.trainingtracker.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.akolata.trainingtracker.core.entity.User;
import pl.akolata.trainingtracker.user.service.UserRepository;
import pl.akolata.trainingtracker.user.service.UsersProfilesService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DatabaseUserProfilesService implements UsersProfilesService {
    private final UserRepository userRepository;

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }
}
