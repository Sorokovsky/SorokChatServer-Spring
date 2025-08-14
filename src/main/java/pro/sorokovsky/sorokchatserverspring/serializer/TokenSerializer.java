package pro.sorokovsky.sorokchatserverspring.serializer;

import pro.sorokovsky.sorokchatserverspring.contract.Token;

import java.util.function.Function;

public interface TokenSerializer extends Function<Token, String> {
}
