package pl.akolata.trainingtracker.exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.akolata.trainingtracker.exercise.entity.Exercise;

@Repository
public interface ExercisesRepository extends JpaRepository<Exercise, Long> {
    boolean existsByNameAndType(String name, Exercise.ExerciseType type);
}
