package pro.sorokovsky.sorokchatserverspring.serializer;

import com.nimbusds.jwt.JWTClaimsSet;
import pro.sorokovsky.sorokchatserverspring.constants.TokensConstants;
import pro.sorokovsky.sorokchatserverspring.contract.Token;

import java.sql.Date;

public abstract class JwtTokenSerializer implements TokenSerializer {
    protected JWTClaimsSet generateClaims(Token token) {
        return new JWTClaimsSet.Builder()
                .jwtID(token.id().toString())
                .issueTime(Date.from(token.createdAt()))
                .expirationTime(Date.from(token.expiresAt()))
                .subject(token.subject())
                .claim(TokensConstants.Authorities.name(), String.join(",", token.authorities()))
                .build();
    }

}
