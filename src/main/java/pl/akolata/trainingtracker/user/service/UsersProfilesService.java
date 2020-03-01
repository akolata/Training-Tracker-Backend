package pl.akolata.trainingtracker.user.service;

import pl.akolata.trainingtracker.core.entity.User;

import java.util.Optional;

public interface UsersProfilesService {
    Optional<User> findUserById(Long id);
}
