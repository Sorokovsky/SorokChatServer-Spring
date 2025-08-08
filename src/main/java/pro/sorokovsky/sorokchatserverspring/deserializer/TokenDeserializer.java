package pro.sorokovsky.sorokchatserverspring.deserializer;

import pro.sorokovsky.sorokchatserverspring.contract.Token;

import java.util.function.Function;

public interface TokenDeserializer extends Function<String, Token> {
}
