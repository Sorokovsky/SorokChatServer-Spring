package pro.sorokovsky.sorokchatserverspring.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pro.sorokovsky.sorokchatserverspring.exception.base.BadRequestException;
import pro.sorokovsky.sorokchatserverspring.exception.base.InternalServerException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    private final MessageSource messageSource;

    @ExceptionHandler({BindException.class})
    public ResponseEntity<Map<String, String>> handleBindException(BindException exception) {
        final var errors = new HashMap<String, String>();
        exception.getBindingResult().getFieldErrors().forEach((error) -> {
            final var fieldName = error.getField();
            final var message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException exception) {
        LOGGER.info(exception.getMessage());
        return ResponseEntity.badRequest().body(getMessage(exception.getErrorCode()));
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<String> handleInternalServerException(InternalServerException exception) {
        LOGGER.error(exception.getMessage(), exception);
        return ResponseEntity.internalServerError().body(getMessage(exception.getErrorCode()));
    }

    private String getMessage(String errorCode) {
        final var defaultMessage = messageSource.getMessage(errorCode, new Object[0], Locale.ENGLISH);
        return "\"" + messageSource.getMessage(errorCode, new Object[0], defaultMessage, LocaleContextHolder.getLocale()) + "\"";
    }
}
