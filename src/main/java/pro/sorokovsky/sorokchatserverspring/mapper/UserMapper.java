package pro.sorokovsky.sorokchatserverspring.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pro.sorokovsky.sorokchatserverspring.contract.GetUserPayload;
import pro.sorokovsky.sorokchatserverspring.contract.NewUserPayload;
import pro.sorokovsky.sorokchatserverspring.contract.UpdateUserPayload;
import pro.sorokovsky.sorokchatserverspring.entity.UserEntity;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;

import java.sql.Date;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public UserModel toModel(UserEntity entity) {
        return UserModel
                .builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .middleName(entity.getMiddleName())
                .password(entity.getPassword())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public UserEntity toEntity(NewUserPayload payload) {
        final var now = Date.from(Instant.now());
        return UserEntity
                .builder()
                .email(payload.email())
                .firstName(payload.firstName())
                .lastName(payload.lastName())
                .middleName(payload.middleName())
                .password(passwordEncoder.encode(payload.password()))
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public UserEntity merge(UserModel oldState, UpdateUserPayload newState) {
        return UserEntity
                .builder()
                .id(oldState.getId())
                .createdAt(oldState.getCreatedAt())
                .updatedAt(Date.from(Instant.now()))
                .email(chooseUpdated(oldState.getEmail(), newState.email()))
                .firstName(chooseUpdated(oldState.getFirstName(), newState.firstName()))
                .lastName(chooseUpdated(oldState.getLastName(), newState.lastName()))
                .middleName(chooseUpdated(oldState.getMiddleName(), newState.middleName()))
                .password(choosePassword(oldState.getPassword(), newState.password()))
                .build();
    }

    private String chooseUpdated(String older, String newer) {
        if (newer == null || newer.isBlank()) return older;
        return newer;
    }

    private String choosePassword(String older, String newer) {
        if (newer == null || newer.isBlank()) return older;
        return passwordEncoder.encode(newer);
    }

    public GetUserPayload toGet(UserModel model) {
        return new GetUserPayload(
                model.getId(),
                model.getCreatedAt(),
                model.getUpdatedAt(),
                model.getEmail(),
                model.getFirstName(),
                model.getLastName(),
                model.getMiddleName()
        );
    }
}