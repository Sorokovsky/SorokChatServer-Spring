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
import pro.sorokovsky.sorokchatserverspring.constants.ErrorsConstants;
import pro.sorokovsky.sorokchatserverspring.exception.*;

import java.util.HashMap;
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

    @ExceptionHandler({InvalidCredentialsException.class})
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException exception) {
        final var locale = LocaleContextHolder.getLocale();
        LOGGER.info(exception.getMessage());
        return ResponseEntity
                .badRequest()
                .body(messageSource.getMessage(ErrorsConstants.INVALID_CREDENTIALS, new Object[0], locale));
    }

    @ExceptionHandler({InvalidTokenException.class})
    public ResponseEntity<String> handleInvalidTokenException(InvalidTokenException exception) {
        final var locale = LocaleContextHolder.getLocale();
        LOGGER.info(exception.getMessage());
        return ResponseEntity
                .badRequest()
                .body(messageSource.getMessage(ErrorsConstants.INVALID_TOKEN, new Object[0], locale));
    }

    @ExceptionHandler({TokenNotParsedException.class, TokenNotSerializeException.class})
    public ResponseEntity<String> handleTokenNotParsedOrNotSerializeException(Exception exception) {
        LOGGER.error(exception.getMessage(), exception);
        final var locale = LocaleContextHolder.getLocale();
        return ResponseEntity
                .internalServerError()
                .body(messageSource.getMessage(ErrorsConstants.UNKNOWN, new Object[0], locale));
    }

    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        final var locale = LocaleContextHolder.getLocale();
        LOGGER.info(exception.getMessage());
        return ResponseEntity
                .badRequest()
                .body(messageSource.getMessage(ErrorsConstants.USER_ALREADY_EXISTS, new Object[0], locale));
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException exception) {
        final var locale = LocaleContextHolder.getLocale();
        LOGGER.info(exception.getMessage());
        return ResponseEntity
                .badRequest()
                .body(messageSource.getMessage(ErrorsConstants.USER_NOT_FOUND, new Object[0], locale));
    }
}
