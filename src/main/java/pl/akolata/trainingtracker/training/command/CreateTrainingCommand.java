package pl.akolata.trainingtracker.training.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTrainingCommand {
    @NotNull
    private final LocalDate date;
    @NotNull
    private final Long gymId;
    @NotNull
    private final Long userId;
    private final String additionalInfo;
    private final String name;
}
