package pl.akolata.trainingtracker.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.akolata.trainingtracker.gym.entity.Gym;

public interface GymRepository extends JpaRepository<Gym, Long>, JpaSpecificationExecutor<Gym> {
    boolean existsByName(String name);
}
