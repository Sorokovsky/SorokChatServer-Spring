package pro.sorokovsky.sorokchatserverspring.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import pro.sorokovsky.sorokchatserverspring.constants.ErrorsConstants;

@Schema(description = "Данні нового повідомлення")
public record NewMessagePayload(
        @NotNull(message = "{" + ErrorsConstants.NOT_EMPTY + "}")
        @Schema(
                description = "Текст повідомлення",
                requiredMode = Schema.RequiredMode.REQUIRED,
                defaultValue = "Андрій створив крутий месенджер"
        )
        String text
) {
}
