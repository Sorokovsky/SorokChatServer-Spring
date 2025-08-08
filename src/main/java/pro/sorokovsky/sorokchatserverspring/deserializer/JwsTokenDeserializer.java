package pro.sorokovsky.sorokchatserverspring.deserializer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.sorokovsky.sorokchatserverspring.contract.Token;
import pro.sorokovsky.sorokchatserverspring.exception.InvalidTokenException;
import pro.sorokovsky.sorokchatserverspring.exception.TokenNotParsedException;

import java.text.ParseException;

@RequiredArgsConstructor
@AllArgsConstructor
public class JwsTokenDeserializer extends JwtTokenDeserializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwsTokenDeserializer.class);

    private final JWSVerifier verifier;

    @Override
    public Token apply(String string) {
        try {
            final var signed = SignedJWT.parse(string);
            signed.verify(verifier);
            return extractFromClaims(signed.getJWTClaimsSet());
        } catch (ParseException exception) {
            LOGGER.error(exception.getMessage(), exception);
            throw new TokenNotParsedException(exception.getMessage(), exception);
        } catch (JOSEException exception) {
            LOGGER.warn(exception.getMessage(), exception);
            throw new InvalidTokenException(exception.getMessage(), exception);
        }
    }
}
