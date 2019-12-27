package pl.akolata.trainingtracker.training.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.akolata.trainingtracker.training.dto.TrainingDto;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
class TrainingQueryResponse {
    private Set<TrainingDto> trainings;
}
