package pro.sorokovsky.sorokchatserverspring.exception;

public class TokenNotSerializeException extends RuntimeException {
    public TokenNotSerializeException() {
        this("Token not serialize");
    }

    public TokenNotSerializeException(String message) {
        super(message);
    }

    public TokenNotSerializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenNotSerializeException(Throwable cause) {
        super(cause);
    }

    public TokenNotSerializeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
