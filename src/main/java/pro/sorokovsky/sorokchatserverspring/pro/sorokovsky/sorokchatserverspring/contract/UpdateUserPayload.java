package pro.sorokovsky.sorokchatserverspring.contract;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import pro.sorokovsky.sorokchatserverspring.constants.ErrorsConstants;

public record UpdateUserPayload(
        @Email(message = "{" + ErrorsConstants.NOT_EMAIL + "}")
        String email,

        @Size(min = 6, message = "{" + ErrorsConstants.INVALID_SIZE + "}")
        String password,

        String firstName,

        String lastName,

        String middleName
) {
}
