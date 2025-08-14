package pro.sorokovsky.sorokchatserverspring.contract;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateChannelPayload(
        @Schema(
                description = "Назва чату",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                defaultValue = "Крутий чат"
        )
        String name,

        @Schema(
                description = "Опис крутого чату",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                defaultValue = "Дуже крутий чат"
        )
        String description
) {
}
