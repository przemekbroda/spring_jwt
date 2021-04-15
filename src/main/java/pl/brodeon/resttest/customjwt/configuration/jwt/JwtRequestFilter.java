package pl.brodeon.resttest.customjwt.configuration.jwt;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.brodeon.resttest.customjwt.configuration.CustomUser;
import pl.brodeon.resttest.customjwt.configuration.JwtUserDetailsService;
import pl.brodeon.resttest.customjwt.service.implementation.JwtServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtServiceImpl jwtService;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        var authenticationHeader = httpServletRequest.getHeader("Authorization");

        if (Strings.isNullOrEmpty(authenticationHeader) || !authenticationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        var jwt = authenticationHeader.replace("Bearer ", "");

        try {
            var claims = jwtService.getClaimsFromAccessToken(jwt);
            var userDetails = (CustomUser) userDetailsService.loadUserByUsername(jwtService.getUsernameFromClaims(claims));
            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (Exception ignored) {
            ignored.printStackTrace();
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "");
        }
    }
}
