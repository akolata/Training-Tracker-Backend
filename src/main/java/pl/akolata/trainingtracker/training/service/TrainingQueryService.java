package pl.akolata.trainingtracker.training.service;

import org.springframework.data.domain.Page;
import pl.akolata.trainingtracker.training.entity.Training;
import pl.akolata.trainingtracker.training.query.TrainingQuery;

public interface TrainingQueryService {
    Page<Training> findTrainingsBy(TrainingQuery query);
}
