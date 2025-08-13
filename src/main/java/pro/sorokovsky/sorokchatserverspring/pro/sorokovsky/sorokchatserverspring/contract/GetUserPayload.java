package pro.sorokovsky.sorokchatserverspring.contract;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(description = "Отриманий користувач")
public record GetUserPayload(
        @Schema(
                description = "Унікальний ідентифікатор",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "252"
        )
        Long id,
        @Schema(
                description = "Дата створення користувача",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "2025-08-09T18:12:23Z"
        )
        Date createdAt,
        @Schema(
                description = "Дата оновлення користувача",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "2025-08-09T18:12:23Z"
        )
        Date updatedAt,
        @Schema(
                description = "Електронна адреса користувача",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "Sorokovskys@ukr.net"
        )
        String email,
        @Schema(
                description = "Ім'я користувача",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "Андрій"
        )
        String firstName,
        @Schema(
                description = "Прізвище користувача",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "Сороковський"
        )
        String lastName,
        @Schema(
                description = "По-батькові користувача",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "Іванович"
        )
        String middleName
) {
}
