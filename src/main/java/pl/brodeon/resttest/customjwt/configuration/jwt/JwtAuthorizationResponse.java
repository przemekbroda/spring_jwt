package pl.brodeon.resttest.customjwt.configuration.jwt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthorizationResponse {
    private String accessToken;
    private String refreshToken;
}
