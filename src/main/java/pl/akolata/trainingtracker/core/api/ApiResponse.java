package pl.akolata.trainingtracker.core.api;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private Boolean success;
    private T response;

    private ApiResponse(T response, boolean success) {
        this.success = success;
        this.response = response;
    }

    public static <T> ApiResponse<T> success(T response) {
        return new ApiResponse<>(response, true);
    }

    public static <T> ApiResponse<T> failure(T response) {
        return new ApiResponse<T>(response, false);
    }
}
