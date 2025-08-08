package pro.sorokovsky.sorokchatserverspring.deserializer;

import com.nimbusds.jwt.JWTClaimsSet;
import pro.sorokovsky.sorokchatserverspring.constants.TokensConstants;
import pro.sorokovsky.sorokchatserverspring.contract.Token;

import java.text.ParseException;
import java.util.UUID;
import java.util.stream.Stream;

public abstract class JwtTokenDeserializer implements TokenDeserializer {
    protected Token extractFromClaims(JWTClaimsSet claims) throws ParseException {
        final var authorities = Stream
                .of(claims.getStringClaim(TokensConstants.Authorities.name()).split(","))
                .toList();
        return new Token(UUID.fromString(
                claims.getJWTID()),
                claims.getSubject(),
                authorities,
                claims.getIssueTime().toInstant(),
                claims.getExpirationTime().toInstant()
        );
    }
}
