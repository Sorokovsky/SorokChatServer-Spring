package pro.sorokovsky.sorokchatserverspring.factory;

import org.springframework.security.core.Authentication;
import pro.sorokovsky.sorokchatserverspring.contract.Token;

import java.util.function.Function;

public interface RefreshTokenFactory extends Function<Authentication, Token> {
}
