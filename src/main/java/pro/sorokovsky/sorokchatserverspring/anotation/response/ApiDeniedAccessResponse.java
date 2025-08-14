package pro.sorokovsky.sorokchatserverspring.anotation.response;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ApiResponse(
        responseCode = "403",
        description = "В доступі відказано"
)
public @interface ApiDeniedAccessResponse {
}
