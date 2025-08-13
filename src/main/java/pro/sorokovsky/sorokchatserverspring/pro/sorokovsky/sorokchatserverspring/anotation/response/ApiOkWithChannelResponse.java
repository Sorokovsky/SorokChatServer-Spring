package pro.sorokovsky.sorokchatserverspring.anotation.response;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import pro.sorokovsky.sorokchatserverspring.contract.GetChannelPayload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ApiResponse(
        description = "Успішно",
        responseCode = "200",
        content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = GetChannelPayload.class)
        )
)
public @interface ApiOkWithChannelResponse {
}
