package pl.brodeon.resttest.customjwt.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import pl.brodeon.resttest.customjwt.configuration.CustomUser;
import pl.brodeon.resttest.customjwt.configuration.jwt.JwtAuthorizationResponse;
import pl.brodeon.resttest.customjwt.rest.model.request.RefreshAccessTokenRequest;
import pl.brodeon.resttest.customjwt.service.JwtService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api")
public class AuthenticationRestController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @PostMapping("/refreshAccessToken")
    public @ResponseBody ResponseEntity<JwtAuthorizationResponse> refreshAccessToken(@Valid @RequestBody RefreshAccessTokenRequest refreshAccessTokenRequest) {
        var claims = jwtService.getClaimsFromAccessToken(refreshAccessTokenRequest.getRefreshToken());
        var userDetails = (CustomUser) jwtUserDetailsService.loadUserByUsername(claims.getSubject());
        var accessToken = jwtService.generateAccessToken(userDetails);
        var newRefreshToken = jwtService.generateRefreshToken(userDetails);

        var response = JwtAuthorizationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/something")
    public @ResponseBody Long doSomething(Authentication authentication) {
        return ((CustomUser) authentication.getPrincipal()).getUserId();
    }

    @GetMapping("/something2")
    public @ResponseBody Long doSomething2(@AuthenticationPrincipal CustomUser customUser) {
        return customUser.getUserId();
    }

}
