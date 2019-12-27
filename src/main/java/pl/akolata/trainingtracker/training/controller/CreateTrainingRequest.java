package pl.akolata.trainingtracker.training.controller;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
class CreateTrainingRequest {

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @NotNull
    private Long userId;

    private Long gymId;
    private String additionalInfo;
    private String name;
}
