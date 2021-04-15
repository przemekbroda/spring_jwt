package pl.brodeon.resttest.customjwt.configuration.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.MimeTypeUtils;
import pl.brodeon.resttest.customjwt.configuration.CustomUser;
import pl.brodeon.resttest.customjwt.service.JwtService;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService, String filterProcessesUrl) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.setFilterProcessesUrl(filterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            var jwtAuthorizationRequest = new ObjectMapper().readValue(request.getInputStream(), JwtAuthorizationRequest.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtAuthorizationRequest.getUsername(), jwtAuthorizationRequest.getPassword()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        var userDetails = (CustomUser) authResult.getPrincipal();
        var accessToken = jwtService.generateAccessToken(userDetails);
        var refreshToken = jwtService.generateRefreshToken(userDetails);

        var responseBody = JwtAuthorizationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        var objectMapper = new ObjectMapper();
        var responseString = objectMapper.writeValueAsString(responseBody);

        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseString);
    }
}
