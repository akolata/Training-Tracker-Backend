package pl.akolata.trainingtracker.training.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.akolata.trainingtracker.gym.model.api.GymResponse;
import pl.akolata.trainingtracker.training.model.dto.TrainingSetDto;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TrainingResponse {
    private Long id;
    private LocalDate date;
    private String name;
    private String additionalInfo;
    private GymResponse gym;
    /**
     * Variable is not named "sets" because of MapStruct bug
     * https://github.com/mapstruct/mapstruct/issues/1799
     */
    private Set<TrainingSetDto> trainingSets;
}
