package pro.sorokovsky.sorokchatserverspring.exception.user;

import pro.sorokovsky.sorokchatserverspring.constants.ErrorsConstants;
import pro.sorokovsky.sorokchatserverspring.exception.base.BadRequestException;

public class UserNotFoundException extends BadRequestException {

    public UserNotFoundException() {
        super(ErrorsConstants.USER_NOT_FOUND);
    }

    public UserNotFoundException(String message) {
        super(ErrorsConstants.USER_NOT_FOUND, message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(ErrorsConstants.USER_NOT_FOUND, message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(ErrorsConstants.USER_NOT_FOUND, cause);
    }
}
