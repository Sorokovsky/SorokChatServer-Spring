package pro.sorokovsky.sorokchatserverspring.contract;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.List;

@Schema(description = "Отриманий чат")
public record GetChannelPayload(
        @Schema(
                description = "Унікальний ідентифікатор",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "1"
        )
        Long id,
        @Schema(
                description = "Дата створення чату",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "2025-08-09T18:12:23Z"
        )
        Date createdAt,
        @Schema(
                description = "Дата оновлення чату",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "2025-08-09T18:12:23Z"
        )
        Date updatedAt,
        @Schema(
                description = "Назва чату",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "Крутий чат"
        )
        String name,
        @ArraySchema(
                arraySchema = @Schema(
                        description = "Опис чату",
                        requiredMode = Schema.RequiredMode.REQUIRED,
                        example = "Опис крутого чату"
                )
        )
        String description,
        List<GetUserPayload> users
) {
}
