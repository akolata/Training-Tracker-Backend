package pl.akolata.trainingtracker.core.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorsResponse {
    private Map<String, Set<String>> errors = new HashMap<>();
}
