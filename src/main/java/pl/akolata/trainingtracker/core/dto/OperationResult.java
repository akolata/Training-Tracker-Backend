package pl.akolata.trainingtracker.core.dto;

import lombok.NonNull;
import lombok.Value;

@Value
public class OperationResult<T> {
    private final T result;
    private final ValidationResult validationResult;

    public static <T> OperationResult<T> success(@NonNull T data) {
        return new OperationResult<>(data, ValidationResult.valid());
    }

    public static <T> OperationResult<T> failure(@NonNull ValidationResult invalidResult) {
        return new OperationResult<>(null, invalidResult);
    }

    public boolean isFailure() {
        return validationResult.notValid();
    }

    public String getErrorMSg() {
        return validationResult.getErrorMsg();
    }
}
