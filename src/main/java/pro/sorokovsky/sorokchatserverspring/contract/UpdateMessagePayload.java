package pro.sorokovsky.sorokchatserverspring.contract;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateMessagePayload(
        @Schema(
                description = "Текст повідомлення",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                defaultValue = "Андрій створив крутий месенджер"
        )
        String text
) {
}
