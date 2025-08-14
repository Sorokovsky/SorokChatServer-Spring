package pro.sorokovsky.sorokchatserverspring.exception.base;

import org.springframework.http.HttpStatus;

public class InternalServerException extends HttpException {
    public InternalServerException(String errorCode) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, errorCode);
    }

    public InternalServerException(String errorCode, String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, errorCode, message);
    }

    public InternalServerException(String errorCode, String message, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, errorCode, message, cause);
    }

    public InternalServerException(String errorCode, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, errorCode, cause);
    }
}
