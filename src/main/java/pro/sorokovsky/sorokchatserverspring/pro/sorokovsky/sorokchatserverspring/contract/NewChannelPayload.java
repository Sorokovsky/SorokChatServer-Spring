package pro.sorokovsky.sorokchatserverspring.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Данні для нового чату")
public record NewChannelPayload(
        @NotNull
        @Schema(
                description = "Назва чату",
                requiredMode = Schema.RequiredMode.REQUIRED,
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
