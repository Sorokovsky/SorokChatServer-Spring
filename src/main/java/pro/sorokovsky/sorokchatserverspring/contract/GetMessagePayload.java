package pro.sorokovsky.sorokchatserverspring.contract;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(description = "Данні повідомлення")
public record GetMessagePayload(
        @Schema(
                description = "Унікальний ідентифікатор",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "1"
        )
        Long id,
        @Schema(
                description = "Дата створення повідомлення",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "2025-08-09T18:12:23Z"
        )
        Date createdAt,
        @Schema(
                description = "Дата оновлення повідомлення",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "2025-08-09T18:12:23Z"
        )
        Date updatedAt,

        @Schema(
                description = "Текст повідомлення",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "Андрій створив крутий месенджер"
        )
        String text,

        @Schema(
                description = "Автор повідомлення",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        GetUserPayload author
) {
}
