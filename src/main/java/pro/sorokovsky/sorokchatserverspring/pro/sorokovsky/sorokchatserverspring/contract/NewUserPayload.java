package pro.sorokovsky.sorokchatserverspring.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pro.sorokovsky.sorokchatserverspring.constants.ErrorsConstants;

@Schema(description = "Данні нового користувача")
public record NewUserPayload(
        @NotNull(message = "{" + ErrorsConstants.NOT_EMPTY + "}")
        @Email(message = "{" + ErrorsConstants.NOT_EMAIL + "}")
        @Schema(
                description = "Електронна адреса",
                defaultValue = "Sorokovskys@urk.net",
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
        String password,

        @Schema(
                description = "Ім'я користувача",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                defaultValue = "Андрій"
        )
        String firstName,

        @Schema(
                description = "Прізвише користувача",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                defaultValue = "Сороковський"
        )
        String lastName,

        @Schema(
                description = "Побатькові користувача",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                defaultValue = "Іванович"
        )
        String middleName
) {
}
