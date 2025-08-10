package pro.sorokovsky.sorokchatserverspring.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import pro.sorokovsky.sorokchatserverspring.contract.Token;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationUserDetailsService
        implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
    private final UsersService usersService;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken authenticatedAuthenticationToken)
            throws UsernameNotFoundException {
        if (authenticatedAuthenticationToken.getPrincipal() instanceof Token token) {
            final var exception = new UsernameNotFoundException("Email invalid");
            final UserModel user = usersService.getByEmail(token.subject()).orElseThrow(() -> exception);
            user.getAuthorities().addAll(authenticatedAuthenticationToken.getAuthorities());
            return user;
        } else {
            throw new UsernameNotFoundException("Invalid token");
        }
    }
}
