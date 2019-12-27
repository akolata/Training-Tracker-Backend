package pl.akolata.trainingtracker.training.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.akolata.trainingtracker.training.entity.Training;
import pl.akolata.trainingtracker.training.query.TrainingQuery;
import pl.akolata.trainingtracker.training.repository.TrainingsRepository;
import pl.akolata.trainingtracker.training.service.TrainingQueryService;

@Service
@RequiredArgsConstructor
class DatabaseTrainingQueryService implements TrainingQueryService {

    private final TrainingsRepository trainingsRepository;

    @Override
    public Page<Training> findTrainingsBy(TrainingQuery query) {
        return trainingsRepository.findAll(query.toSpecification(), query.getPageable());
    }
}
