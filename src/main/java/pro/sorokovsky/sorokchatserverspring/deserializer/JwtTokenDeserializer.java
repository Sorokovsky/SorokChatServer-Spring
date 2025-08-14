package pro.sorokovsky.sorokchatserverspring.deserializer;

import com.nimbusds.jwt.JWTClaimsSet;
import pro.sorokovsky.sorokchatserverspring.contract.Token;

import java.text.ParseException;
import java.util.UUID;

public abstract class JwtTokenDeserializer implements TokenDeserializer {
    protected Token extractFromClaims(JWTClaimsSet claims) throws ParseException {
        return new Token(UUID.fromString(
                claims.getJWTID()),
                claims.getSubject(),
                claims.getIssueTime().toInstant(),
                claims.getExpirationTime().toInstant()
        );
    }
}
