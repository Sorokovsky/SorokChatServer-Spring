package pro.sorokovsky.sorokchatserverspring.configuration;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.sorokovsky.sorokchatserverspring.deserializer.JwsTokenDeserializer;
import pro.sorokovsky.sorokchatserverspring.deserializer.TokenDeserializer;
import pro.sorokovsky.sorokchatserverspring.serializer.JweTokenSerializer;
import pro.sorokovsky.sorokchatserverspring.serializer.JwsTokenSerializer;
import pro.sorokovsky.sorokchatserverspring.serializer.TokenSerializer;

import java.text.ParseException;

@Configuration
public class TokensConfiguration {
    @Bean
    @Qualifier("jws-serializer")
    public TokenSerializer jwsTokenSerializer(@Value("${tokens.signing-key}") String signingKey)
            throws ParseException, KeyLengthException {
        return new JwsTokenSerializer(new MACSigner(OctetSequenceKey.parse(signingKey)));
    }

    @Bean
    @Qualifier("jws-deserializer")
    public TokenDeserializer jwsTokenDeserializer(@Value("${tokens.signing-key}") String signingKey)
            throws ParseException, JOSEException {
        return new JwsTokenDeserializer(new MACVerifier(OctetSequenceKey.parse(signingKey)));
    }

    @Bean
    @Qualifier("jwe-serializer")
    public TokenSerializer jweTokenSerializer(@Value("${tokens.encryption-key}") String encryptionKey) throws ParseException, KeyLengthException {
        return new JweTokenSerializer(new DirectEncrypter(OctetSequenceKey.parse(encryptionKey)));
    }
}
