package pro.sorokovsky.sorokchatserverspring.service;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import pro.sorokovsky.sorokchatserverspring.contract.Token;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationUserDetailsService
        implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationUserDetailsService.class);

    private final UsersService usersService;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken authenticatedAuthenticationToken)
            throws UsernameNotFoundException {
        if (authenticatedAuthenticationToken.getPrincipal() instanceof Token token) {
            var user = usersService.loadUserByUsername(token.subject());
            LOGGER.info("Loaded user: {}", user);
            return user;
        } else {
            throw new UsernameNotFoundException("Invalid token");
        }
    }
}
