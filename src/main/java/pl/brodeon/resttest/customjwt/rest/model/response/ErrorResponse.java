package pl.brodeon.resttest.customjwt.rest.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ErrorResponse {
    private String message;
    private Instant timestamp;
    private int status;
}
