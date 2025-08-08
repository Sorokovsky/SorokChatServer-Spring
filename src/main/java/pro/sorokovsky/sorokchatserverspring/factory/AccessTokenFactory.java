package pro.sorokovsky.sorokchatserverspring.factory;

import pro.sorokovsky.sorokchatserverspring.contract.Token;

import java.util.function.Function;

public interface AccessTokenFactory extends Function<Token, Token> {
}
