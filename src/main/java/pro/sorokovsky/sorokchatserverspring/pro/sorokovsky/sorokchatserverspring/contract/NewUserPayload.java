package pro.sorokovsky.sorokchatserverspring.contract;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pro.sorokovsky.sorokchatserverspring.constants.ErrorsConstants;

public record NewUserPayload(
        @NotNull(message = "{" + ErrorsConstants.NOT_EMPTY + "}")
        @Email(message = "{" + ErrorsConstants.NOT_EMAIL + "}")
        String email,

        @NotNull(message = "{" + ErrorsConstants.NOT_EMPTY + "}")
        @Size(min = 6, message = "{" + ErrorsConstants.INVALID_SIZE + "}")
        String password,

        String firstName,

        String lastName,

        String middleName
) {
}
