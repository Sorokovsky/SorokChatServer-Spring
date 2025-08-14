package pro.sorokovsky.sorokchatserverspring.serializer;

import com.nimbusds.jose.*;
import com.nimbusds.jwt.EncryptedJWT;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.sorokovsky.sorokchatserverspring.contract.Token;
import pro.sorokovsky.sorokchatserverspring.exception.token.TokenNotSerializeException;

@RequiredArgsConstructor
@AllArgsConstructor
public class JweTokenSerializer extends JwtTokenSerializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JweTokenSerializer.class);

    private final JWEEncrypter encrypter;
    private JWEAlgorithm algorithm = JWEAlgorithm.DIR;
    private EncryptionMethod method = EncryptionMethod.A192GCM;

    public JweTokenSerializer algorithm(JWEAlgorithm algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public JweTokenSerializer method(EncryptionMethod method) {
        this.method = method;
        return this;
    }

    @Override
    public String apply(Token token) {
        final var header = new JWEHeader.Builder(algorithm, method)
                .keyID(token.id().toString())
                .build();
        final var encrypted = new EncryptedJWT(header, generateClaims(token));
        try {
            encrypted.encrypt(encrypter);
            return encrypted.serialize();
        } catch (JOSEException exception) {
            LOGGER.error(exception.getMessage(), exception);
            throw new TokenNotSerializeException(exception.getMessage(), exception);
        }
    }
}
