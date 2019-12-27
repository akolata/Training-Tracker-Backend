package pl.akolata.trainingtracker.training.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.akolata.trainingtracker.training.dto.TrainingSetDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
class TrainingSetResponse {
    TrainingSetDto trainingSet;
}
