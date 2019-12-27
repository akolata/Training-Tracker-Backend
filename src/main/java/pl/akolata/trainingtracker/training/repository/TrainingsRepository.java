package pl.akolata.trainingtracker.training.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.akolata.trainingtracker.training.entity.Training;

public interface TrainingsRepository extends JpaRepository<Training, Long>, JpaSpecificationExecutor<Training> {
    boolean existsByIdAndUserId(Long trainingId, Long userId);

    boolean existsById(Long trainingId);
}
