package pl.brodeon.resttest.customjwt.rest.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RefreshAccessTokenRequest {
    @NotBlank(message = "jest jeszcze bardziej puste")
    private String refreshToken;
}
