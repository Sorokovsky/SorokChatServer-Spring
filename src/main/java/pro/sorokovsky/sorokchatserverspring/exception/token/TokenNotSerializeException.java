package pro.sorokovsky.sorokchatserverspring.exception.token;

import pro.sorokovsky.sorokchatserverspring.constants.ErrorsConstants;
import pro.sorokovsky.sorokchatserverspring.exception.base.InternalServerException;

public class TokenNotSerializeException extends InternalServerException {

    public TokenNotSerializeException() {
        super(ErrorsConstants.UNKNOWN);
    }

    public TokenNotSerializeException(String message) {
        super(ErrorsConstants.UNKNOWN, message);
    }

    public TokenNotSerializeException(String message, Exception cause) {
        super(ErrorsConstants.UNKNOWN, message, cause);
    }

    public TokenNotSerializeException(Throwable cause) {
        super(ErrorsConstants.UNKNOWN, cause);
    }
}
