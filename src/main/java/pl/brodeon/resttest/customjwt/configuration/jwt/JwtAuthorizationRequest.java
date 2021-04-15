package pl.brodeon.resttest.customjwt.configuration.jwt;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class JwtAuthorizationRequest {
    private String username;
    private String password;
}
