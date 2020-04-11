package pl.akolata.trainingtracker.user.service.impl;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.akolata.trainingtracker.core.domain.UserPrincipal;
import pl.akolata.trainingtracker.core.entity.User;
import pl.akolata.trainingtracker.user.service.UserRepository;

@Service
@AllArgsConstructor
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with username [%s] not found", username)));
        return UserPrincipal.fromUser(user);
    }

    @Transactional
    @Cacheable(cacheNames = "userLoginCache", key = "#id")
    public UserDetails loadUserById(@NonNull Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with id [%d] not found", id)));
        return UserPrincipal.fromUser(user);
    }
}
