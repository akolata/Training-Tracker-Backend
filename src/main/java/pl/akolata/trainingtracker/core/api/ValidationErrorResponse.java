package pl.akolata.trainingtracker.core.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {
    private String errorMsg;
}
