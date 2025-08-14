package pro.sorokovsky.sorokchatserverspring.exception.token;

import pro.sorokovsky.sorokchatserverspring.constants.ErrorsConstants;
import pro.sorokovsky.sorokchatserverspring.exception.base.BadRequestException;

public class InvalidTokenException extends BadRequestException {

    public InvalidTokenException() {
        super(ErrorsConstants.INVALID_TOKEN);
    }

    public InvalidTokenException(String message) {
        super(ErrorsConstants.INVALID_TOKEN, message);
    }

    public InvalidTokenException(String message, Exception cause) {
        super(ErrorsConstants.INVALID_TOKEN, message, cause);
    }

    public InvalidTokenException(Throwable cause) {
        super(ErrorsConstants.INVALID_TOKEN, cause);
    }
}
