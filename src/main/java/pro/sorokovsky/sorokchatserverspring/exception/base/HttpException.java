package pro.sorokovsky.sorokchatserverspring.exception.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class HttpException extends RuntimeException {
    private HttpStatus statusCode;
    private String errorCode;

    public HttpException(HttpStatus statusCode, String errorCode) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }

    public HttpException(HttpStatus statusCode, String errorCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }

    public HttpException(HttpStatus statusCode, String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }

    public HttpException(HttpStatus statusCode, String errorCode, Throwable cause) {
        super(cause);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }
}
