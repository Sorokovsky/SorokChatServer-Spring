package pro.sorokovsky.sorokchatserverspring.exception.user;

import pro.sorokovsky.sorokchatserverspring.constants.ErrorsConstants;
import pro.sorokovsky.sorokchatserverspring.exception.base.BadRequestException;

public class UserAlreadyExistsException extends BadRequestException {

    public UserAlreadyExistsException() {
        super(ErrorsConstants.USER_ALREADY_EXISTS);
    }

    public UserAlreadyExistsException(String message) {
        super(ErrorsConstants.USER_ALREADY_EXISTS, message);
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(ErrorsConstants.USER_ALREADY_EXISTS, message, cause);
    }

    public UserAlreadyExistsException(Throwable cause) {
        super(ErrorsConstants.USER_ALREADY_EXISTS, cause);
    }
}
