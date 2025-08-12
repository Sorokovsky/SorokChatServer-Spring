package pro.sorokovsky.sorokchatserverspring.deserializer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEDecrypter;
import com.nimbusds.jwt.EncryptedJWT;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.sorokovsky.sorokchatserverspring.contract.Token;
import pro.sorokovsky.sorokchatserverspring.exception.InvalidTokenException;
import pro.sorokovsky.sorokchatserverspring.exception.TokenNotParsedException;

import java.text.ParseException;

@RequiredArgsConstructor
public class JweTokenDeserializer extends JwtTokenDeserializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JweTokenDeserializer.class);

    private final JWEDecrypter decrypter;

    @Override
    public Token apply(String string) {
        try {
            if (string == null) throw new TokenNotParsedException("Token is null");
            final var encrypted = EncryptedJWT.parse(string);
            encrypted.decrypt(decrypter);
            return extractFromClaims(encrypted.getJWTClaimsSet());
        } catch (ParseException exception) {
            LOGGER.error(exception.getMessage(), exception);
            throw new TokenNotParsedException(exception.getMessage(), exception);
        } catch (JOSEException exception) {
            throw new InvalidTokenException(exception.getMessage(), exception);
        }
    }
}
