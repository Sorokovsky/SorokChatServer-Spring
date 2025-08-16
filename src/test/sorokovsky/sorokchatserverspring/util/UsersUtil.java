package sorokovsky.sorokchatserverspring.util;

import pro.sorokovsky.sorokchatserverspring.contract.GetUserPayload;
import pro.sorokovsky.sorokchatserverspring.contract.NewUserPayload;
import pro.sorokovsky.sorokchatserverspring.contract.UpdateUserPayload;
import pro.sorokovsky.sorokchatserverspring.entity.UserEntity;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;

import java.util.Date;

import static sorokovsky.sorokchatserverspring.util.TimeUtil.NOW_INSTANT;

public class UsersUtil {
    public static UserModel getUserModel() {
        return UserModel
                .builder()
                .id(1L)
                .createdAt(Date.from(NOW_INSTANT))
                .updatedAt(Date.from(NOW_INSTANT))
                .email("Sorokovskys@ukr.net")
                .password("hashed password")
                .firstName("Andrey")
                .lastName("Sorokovsky")
                .middleName("Ivanovich")
                .build();
    }

    public static UserEntity getUserEntity() {
        return UserEntity
                .builder()
                .id(1L)
                .createdAt(Date.from(NOW_INSTANT))
                .updatedAt(Date.from(NOW_INSTANT))
                .email("Sorokovskys@ukr.net")
                .password("hashed password")
                .firstName("Andrey")
                .lastName("Sorokovsky")
                .middleName("Ivanovich")
                .build();
    }

    public static NewUserPayload getNewUserPayload() {
        return new NewUserPayload(
                "Sorokovskys@ukr.net",
                "password", "Andrey",
                "Sorokovsky",
                "Ivanovich"
        );
    }

    public static GetUserPayload getUserPayload() {
        return new GetUserPayload(1L, Date.from(NOW_INSTANT), Date.from(NOW_INSTANT),
                "Sorokovskys@ukr.net",
                "Andrey",
                "Sorokovsky",
                "Ivanovich"
        );
    }

    public static UpdateUserPayload getNewStateWithPassword() {
        return new UpdateUserPayload(
                null,
                "new password",
                "Андрій",
                "Сороковський",
                "Іванович"
        );
    }

    public static UserEntity getMergedWithPassword() {
        final var oldState = getUserModel();
        final var newState = getNewStateWithPassword();
        return UserEntity
                .builder()
                .id(oldState.getId())
                .email(oldState.getEmail())
                .password("new hashed password")
                .middleName(newState.middleName())
                .firstName(newState.firstName())
                .lastName(newState.lastName())
                .createdAt(oldState.getCreatedAt())
                .updatedAt(Date.from(NOW_INSTANT))
                .build();
    }

    public static UpdateUserPayload getNewStateWithNullPassword() {
        return new UpdateUserPayload(
                "",
                null,
                "Андрій",
                "Сороковський",
                "Іванович"
        );
    }

    public static UserEntity getMergedWithNullPassword() {
        final var oldState = getUserModel();
        final var newState = getNewStateWithNullPassword();
        return UserEntity
                .builder()
                .id(oldState.getId())
                .email(oldState.getEmail())
                .password(oldState.getPassword())
                .middleName(newState.middleName())
                .firstName(newState.firstName())
                .lastName(newState.lastName())
                .createdAt(oldState.getCreatedAt())
                .updatedAt(Date.from(NOW_INSTANT))
                .build();
    }

    public static UpdateUserPayload getNewStateWithEmptyPassword() {
        return new UpdateUserPayload(
                "",
                "",
                "Андрій",
                "Сороковський",
                "Іванович"
        );
    }

    public static UserEntity getMergedWithEmptyPassword() {
        final var oldState = getUserModel();
        final var newState = getNewStateWithEmptyPassword();
        return UserEntity
                .builder()
                .id(oldState.getId())
                .email(oldState.getEmail())
                .password(oldState.getPassword())
                .middleName(newState.middleName())
                .firstName(newState.firstName())
                .lastName(newState.lastName())
                .createdAt(oldState.getCreatedAt())
                .updatedAt(Date.from(NOW_INSTANT))
                .build();
    }
}
