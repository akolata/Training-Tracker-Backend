package pl.akolata.trainingtracker.training.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.akolata.trainingtracker.exercises.repository.ExercisesRepository;
import pl.akolata.trainingtracker.gym.repository.GymRepository;
import pl.akolata.trainingtracker.training.entity.Training;
import pl.akolata.trainingtracker.training.entity.TrainingSet;
import pl.akolata.trainingtracker.user.service.UserRepository;

@RequiredArgsConstructor
public class TrainingsCommandsMapper {

    private final GymRepository gymRepository;
    private final UserRepository userRepository;
    private final ExercisesRepository exercisesRepository;

    public Training toTraining(@NonNull CreateTrainingCommand command) {
        Training training = new Training();
        training.setName(command.getName());
        training.setAdditionalInfo(command.getAdditionalInfo());
        training.setDate(command.getDate());
        if (command.getGymId() != null) {
            gymRepository.findById(command.getGymId()).ifPresent(training::setGym);
        }
        userRepository.findById(command.getUserId()).ifPresent(training::setUser);
        return training;
    }

    public TrainingSet toTrainingSet(@NonNull CreateTrainingSetCommand command) {
        TrainingSet trainingSet = new TrainingSet();

        exercisesRepository.findById(command.getExerciseId()).ifPresent(trainingSet::setExercise);
        trainingSet.setReps(command.getReps());
        trainingSet.setWeight(command.getWeight());
        trainingSet.setCalories(command.getCalories());
        trainingSet.setDurationInMinutes(command.getDurationInMinutes());
        trainingSet.setDistanceInKm(command.getDistanceInKm());
        trainingSet.setAdditionalInfo(command.getAdditionalInfo());

        return trainingSet;
    }
}
