package pro.sorokovsky.sorokchatserverspring.exception.base;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends HttpException {
    public UnauthorizedException(String errorCode) {
        super(HttpStatus.UNAUTHORIZED, errorCode);
    }

    public UnauthorizedException(String errorCode, String message) {
        super(HttpStatus.UNAUTHORIZED, errorCode, message);
    }

    public UnauthorizedException(String errorCode, String message, Throwable cause) {
        super(HttpStatus.UNAUTHORIZED, errorCode, message, cause);
    }

    public UnauthorizedException(String errorCode, Throwable cause) {
        super(HttpStatus.UNAUTHORIZED, errorCode, cause);
    }
}
