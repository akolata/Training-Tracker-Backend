package pl.akolata.trainingtracker.training.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.akolata.trainingtracker.training.dto.TrainingDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
class TrainingResponse {
    private TrainingDto training;
}
