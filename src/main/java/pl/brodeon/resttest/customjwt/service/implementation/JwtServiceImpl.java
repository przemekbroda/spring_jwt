package pl.brodeon.resttest.customjwt.service.implementation;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.brodeon.resttest.customjwt.configuration.CustomUser;
import pl.brodeon.resttest.customjwt.service.JwtService;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {

    private static final long accessTokenLifetime = 60 * 15;
    public static final long refreshTokenLifetime = 60 * 60 * 24 * 30;

    private static final String accessTokenSecret = "0393e944ee8108bb66fc9fa4f99f9c862481e9e0519e18232ba61b0767eee8c6";
    private static final String refreshTokenSecret = "0393e944ee8108bb66fc9fa4g99f9c862481e9e0519e18232ba61b0767eee8c6";

    @Override
    public String generateAccessToken(CustomUser userDetails) {
        var roles = userDetails.getAuthorities()
                .stream()
                .map(r -> r.getAuthority())
                .collect(Collectors.toList());

        return generateJwtToken(Map.of("roles", roles), userDetails.getUsername(), accessTokenSecret, accessTokenLifetime);
    }

    @Override
    public String generateRefreshToken(CustomUser userDetails) {
        return generateJwtToken(new HashMap<>(), userDetails.getUsername(), refreshTokenSecret, refreshTokenLifetime);
    }

    @Override
    public Claims getClaimsFromAccessToken(String jwt) {
        var signatureAlgorithm = SignatureAlgorithm.HS512;
        var signingKey = new SecretKeySpec(accessTokenSecret.getBytes(), signatureAlgorithm.getJcaName());
        return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(jwt).getBody();
    }

    @Override
    public String getUsernameFromClaims(Claims claims) {
        if (claims == null) return null;
        return claims.getSubject();
    }

    private String generateJwtToken(Map<String, Object> claims, String subject, String secret, long lifetime) {
        var signatureAlgorithm = SignatureAlgorithm.HS512;
        var signingKey = new SecretKeySpec(secret.getBytes(), signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .addClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + lifetime * 1000))
                .signWith(signingKey, signatureAlgorithm)
                .compact();
    }



}
