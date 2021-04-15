package pl.brodeon.resttest.customjwt.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import pl.brodeon.resttest.customjwt.configuration.CustomUser;

public interface JwtService {
    String generateAccessToken(CustomUser userDetails);
    String generateRefreshToken(CustomUser userDetails);
    Claims getClaimsFromAccessToken(String jwt);
    String getUsernameFromClaims(Claims claims);
}
