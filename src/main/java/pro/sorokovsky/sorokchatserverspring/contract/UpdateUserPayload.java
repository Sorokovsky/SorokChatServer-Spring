package pro.sorokovsky.sorokchatserverspring.contract;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserPayload(
        @Email(message = "{errors.not-email}")
        String email,

        @Size(min = 6, message = "{errors.invalid-size}")
        String password,

        String firstName,

        String lastName,

        String middleName
) {
}
