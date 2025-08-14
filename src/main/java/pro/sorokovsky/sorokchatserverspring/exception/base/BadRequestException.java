package pro.sorokovsky.sorokchatserverspring.exception.base;

import org.springframework.http.HttpStatus;

public class BadRequestException extends HttpException {
    public BadRequestException(String errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }

    public BadRequestException(String errorCode, String message) {
        super(HttpStatus.BAD_REQUEST, errorCode, message);
    }

    public BadRequestException(String errorCode, String message, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, errorCode, message, cause);
    }

    public BadRequestException(String errorCode, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, errorCode, cause);
    }
}
