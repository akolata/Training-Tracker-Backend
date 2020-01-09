package pl.akolata.trainingtracker.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.akolata.trainingtracker.core.api.ServerErrorResponse;
import pl.akolata.trainingtracker.core.api.ValidationErrorsResponse;

import java.util.*;

@Slf4j
@ControllerAdvice
class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Set<String>> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            appendError(error.getField(), error.getDefaultMessage(), errors);
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            appendError(error.getCode(), error.getDefaultMessage(), errors);
        }
        ValidationErrorsResponse errorsResponse = new ValidationErrorsResponse(errors);
        return ResponseEntity.badRequest().body(errorsResponse);
    }

    // TODO TTB-005 implement other methods first, because this handler will be active ever for 401 errors
//    @ExceptionHandler({Exception.class, RuntimeException.class})
//    public ResponseEntity<ServerErrorResponse> handleServerError(Exception e) {
//        log.error("Server's error has been handled", e);
//        return new ResponseEntity<>(new ServerErrorResponse("server error"), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    private void appendError(String code, String errorMsg, Map<String, Set<String>> errorsForCode) {
        if (Objects.isNull(code)) {
            return;
        }
        code = code.toLowerCase();
        Set<String> errorMessages = errorsForCode.getOrDefault(code, new HashSet<>());
        errorMessages.add(errorMsg);
        errorsForCode.put(code, errorMessages);
    }
}
