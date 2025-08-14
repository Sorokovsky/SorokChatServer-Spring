package pro.sorokovsky.sorokchatserverspring.serializer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jwt.SignedJWT;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.sorokovsky.sorokchatserverspring.contract.Token;
import pro.sorokovsky.sorokchatserverspring.exception.token.TokenNotSerializeException;

@RequiredArgsConstructor
@AllArgsConstructor
public class JwsTokenSerializer extends JwtTokenSerializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwsTokenSerializer.class);

    private final JWSSigner signer;
    private JWSAlgorithm algorithm = JWSAlgorithm.HS256;

    public JwsTokenSerializer algorithm(JWSAlgorithm algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    @Override
    public String apply(Token token) {
        final var header = new JWSHeader.Builder(algorithm)
                .keyID(token.id().toString())
                .build();
        final var signed = new SignedJWT(header, generateClaims(token));
        try {
            signed.sign(signer);
            return signed.serialize();
        } catch (JOSEException exception) {
            LOGGER.error(exception.getMessage(), exception);
            throw new TokenNotSerializeException(exception.getMessage(), exception);
        }
    }
}
