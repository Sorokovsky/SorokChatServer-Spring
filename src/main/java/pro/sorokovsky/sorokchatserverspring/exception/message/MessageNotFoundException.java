package pro.sorokovsky.sorokchatserverspring.exception.message;

import pro.sorokovsky.sorokchatserverspring.constants.ErrorsConstants;
import pro.sorokovsky.sorokchatserverspring.exception.base.BadRequestException;

public class MessageNotFoundException extends BadRequestException {
    public MessageNotFoundException() {
        super(ErrorsConstants.MESSAGE_NOT_FOUND);
    }

    public MessageNotFoundException(String message) {
        super(ErrorsConstants.MESSAGE_NOT_FOUND, message);
    }

    public MessageNotFoundException(String message, Throwable cause) {
        super(ErrorsConstants.MESSAGE_NOT_FOUND, message, cause);
    }

    public MessageNotFoundException(Throwable cause) {
        super(ErrorsConstants.MESSAGE_NOT_FOUND, cause);
    }
}
