package pl.akolata.trainingtracker.training.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import pl.akolata.trainingtracker.gym.dto.GymDto;

import java.time.LocalDate;
import java.util.Set;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TrainingDto {
    private final Long id;
    private final LocalDate date;
    private final String name;
    private final String additionalInfo;
    private final GymDto gym;
    /**
     * Variable is not named "sets" because of MapStruct bug
     * https://github.com/mapstruct/mapstruct/issues/1799
     */
    private final Set<TrainingSetDto> trainingSets;
}
