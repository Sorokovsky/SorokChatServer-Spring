package pro.sorokovsky.sorokchatserverspring.anotation.response;

import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import pro.sorokovsky.sorokchatserverspring.contract.GetUserPayload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ApiResponse(
        description = "Створено",
        responseCode = "201",
        content = @Content(
                schema = @Schema(implementation = GetUserPayload.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
        ),
        headers = {
                @Header(
                        name = HttpHeaders.LOCATION,
                        required = true,
                        example = "/authentication/get-me",
                        description = "Посилання на авторизованого користувача",
                        schema = @Schema(implementation = String.class)
                )
        }
)
public @interface ApiRegisteredResponse {
}
