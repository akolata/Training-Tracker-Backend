package pl.akolata.trainingtracker.gym.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GymDto {
    private final Long id;
    private final String name;
}
