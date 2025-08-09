package pro.sorokovsky.sorokchatserverspring.contract;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewUserPayload(
        @NotNull(message = "{errors.not-empty}")
        @Email(message = "{errors.not-email}")
        String email,

        @NotNull(message = "{errors.not-empty}")
        @Size(min = 6, message = "{errors.invalid-size}")
        String password,

        String firstName,

        String lastName,

        String middleName
) {
}
