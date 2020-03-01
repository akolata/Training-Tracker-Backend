package pl.akolata.trainingtracker.user.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.akolata.trainingtracker.training.model.dto.TrainingDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
class TrainingsResponse {
    private List<TrainingDto> trainings;
}
