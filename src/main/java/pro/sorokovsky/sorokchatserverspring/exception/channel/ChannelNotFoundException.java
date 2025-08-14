package pro.sorokovsky.sorokchatserverspring.exception.channel;

import pro.sorokovsky.sorokchatserverspring.constants.ErrorsConstants;
import pro.sorokovsky.sorokchatserverspring.exception.base.BadRequestException;

public class ChannelNotFoundException extends BadRequestException {
    public ChannelNotFoundException() {
        super(ErrorsConstants.CHANNEL_NOT_FOUND);
    }

    public ChannelNotFoundException(String message) {
        super(ErrorsConstants.CHANNEL_NOT_FOUND, message);
    }

    public ChannelNotFoundException(String message, Throwable cause) {
        super(ErrorsConstants.CHANNEL_NOT_FOUND, message, cause);
    }

    public ChannelNotFoundException(Throwable cause) {
        super(ErrorsConstants.CHANNEL_NOT_FOUND, cause);
    }
}
