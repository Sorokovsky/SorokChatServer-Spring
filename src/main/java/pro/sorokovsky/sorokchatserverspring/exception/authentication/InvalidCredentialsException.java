package pro.sorokovsky.sorokchatserverspring.exception.authentication;

import pro.sorokovsky.sorokchatserverspring.constants.ErrorsConstants;
import pro.sorokovsky.sorokchatserverspring.exception.base.BadRequestException;

public class InvalidCredentialsException extends BadRequestException {
    public InvalidCredentialsException() {
        super(ErrorsConstants.INVALID_CREDENTIALS);
    }

    public InvalidCredentialsException(String message) {
        super(ErrorsConstants.INVALID_CREDENTIALS, message);
    }

    public InvalidCredentialsException(String message, Throwable cause) {
        super(ErrorsConstants.INVALID_CREDENTIALS, message, cause);
    }

    public InvalidCredentialsException(Throwable cause) {
        super(ErrorsConstants.INVALID_CREDENTIALS, cause);
    }
}
