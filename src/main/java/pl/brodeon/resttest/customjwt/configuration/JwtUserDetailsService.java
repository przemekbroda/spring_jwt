package pl.brodeon.resttest.customjwt.configuration;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (!s.equals("Przemek")) {
            throw new UsernameNotFoundException("User with username " + s + " has not been found");
        }

        return new CustomUser(1234, s, "{noop}Test", AuthorityUtils.createAuthorityList("ROLE_USER"));

//        return User.builder()
//                .username(s)
//                .password("{noop}Test")
//                .roles("USER")
//                .build();
    }
}
