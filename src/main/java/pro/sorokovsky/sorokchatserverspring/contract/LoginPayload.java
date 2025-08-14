package pro.sorokovsky.sorokchatserverspring.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pro.sorokovsky.sorokchatserverspring.constants.ErrorsConstants;

@Schema(description = "Данні для авторизації користувача")
public record LoginPayload(
        @NotNull(message = "{" + ErrorsConstants.NOT_EMPTY + "}")
        @Email(message = "{" + ErrorsConstants.NOT_EMAIL + "}")
        @Schema(
                description = "Електронна адреса",
                defaultValue = "Sorokovskys@ukr.net",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String email,

        @NotNull(message = "{" + ErrorsConstants.NOT_EMPTY + "}")
        @Size(min = 6, message = "{" + ErrorsConstants.INVALID_SIZE + "}")
        @Schema(
                description = "Пароль",
                defaultValue = "password",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String password
) {
}
