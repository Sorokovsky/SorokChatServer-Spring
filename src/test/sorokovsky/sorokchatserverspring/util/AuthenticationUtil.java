package sorokovsky.sorokchatserverspring.util;

import pro.sorokovsky.sorokchatserverspring.contract.LoginPayload;

public class AuthenticationUtil {
    public static LoginPayload getLoginPayload() {
        return new LoginPayload("Sorokovskys@ukr.net", "password");
    }

}
