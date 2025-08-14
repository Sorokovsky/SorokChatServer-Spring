package pro.sorokovsky.sorokchatserverspring.exception.token;

import pro.sorokovsky.sorokchatserverspring.constants.ErrorsConstants;
import pro.sorokovsky.sorokchatserverspring.exception.base.InternalServerException;

public class TokenNotParsedException extends InternalServerException {

    public TokenNotParsedException() {
        super(ErrorsConstants.UNKNOWN);
    }

    public TokenNotParsedException(String message) {
        super(ErrorsConstants.UNKNOWN, message);
    }

    public TokenNotParsedException(String message, Exception cause) {
        super(ErrorsConstants.UNKNOWN, message, cause);
    }

    public TokenNotParsedException(Throwable cause) {
        super(ErrorsConstants.UNKNOWN, cause);
    }
}
