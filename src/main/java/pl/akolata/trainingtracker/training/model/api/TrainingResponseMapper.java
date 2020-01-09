package pl.akolata.trainingtracker.training.model.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import pl.akolata.trainingtracker.exercise.model.api.ExerciseResponseMapper;
import pl.akolata.trainingtracker.gym.model.api.GymResponseMapper;
import pl.akolata.trainingtracker.training.entity.Training;
import pl.akolata.trainingtracker.training.entity.TrainingSet;

@Mapper(uses = {GymResponseMapper.class, ExerciseResponseMapper.class}, unmappedTargetPolicy = ReportingPolicy.WARN)
public interface TrainingResponseMapper {

    @Mappings({
            @Mapping(source = "sets", target = "trainingSets")
    })
    TrainingResponse toTrainingResponse(Training training);

    @Mappings({
            @Mapping(target = "trainingId", source = "training.id")
    })
    TrainingSetResponse toTrainingSetResponse(TrainingSet trainingSet);
}
