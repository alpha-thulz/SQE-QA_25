package za.co.wedela.backend.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class APIResponse {

    public static ResponseEntity<?> responseEntity(Object response, HttpStatus status) {
        if (response == null || (response instanceof String && ((String) response).isBlank()))
            return ResponseEntity.status(status).build();
        return ResponseEntity.status(status).body(response);
    }
}