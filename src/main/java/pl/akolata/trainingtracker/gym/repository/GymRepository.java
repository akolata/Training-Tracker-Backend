package pl.akolata.trainingtracker.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.akolata.trainingtracker.gym.entity.Gym;

public interface GymRepository extends JpaRepository<Gym, Long> {
    boolean existsByName(String name);
}
