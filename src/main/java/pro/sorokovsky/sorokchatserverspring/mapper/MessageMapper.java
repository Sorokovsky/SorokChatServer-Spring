package pro.sorokovsky.sorokchatserverspring.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sorokovsky.sorokchatserverspring.contract.NewMessagePayload;
import pro.sorokovsky.sorokchatserverspring.contract.UpdateMessagePayload;
import pro.sorokovsky.sorokchatserverspring.entity.MessageEntity;
import pro.sorokovsky.sorokchatserverspring.model.MessageModel;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class MessageMapper {
    private final UserMapper userMapper;

    public MessageModel toModel(MessageEntity entity) {
        return MessageModel
                .builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .author(userMapper.toModel(entity.getAuthor()))
                .text(entity.getText())
                .build();
    }

    public MessageEntity toEntity(MessageModel model) {
        return MessageEntity
                .builder()
                .id(model.getId())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .author(userMapper.toEntity(model.getAuthor()))
                .text(model.getText())
                .build();
    }

    public MessageEntity merge(MessageModel oldState, UpdateMessagePayload newState) {
        return MessageEntity
                .builder()
                .id(oldState.getId())
                .createdAt(oldState.getCreatedAt())
                .updatedAt(Date.from(Instant.now()))
                .author(userMapper.toEntity(oldState.getAuthor()))
                .text(chooseString(oldState.getText(), newState.text()))
                .build();
    }

    public MessageEntity toEntity(NewMessagePayload payload, UserModel user) {
        final var now = Date.from(Instant.now());
        return MessageEntity
                .builder()
                .createdAt(now)
                .updatedAt(now)
                .author(userMapper.toEntity(user))
                .text(payload.text())
                .build();
    }

    private String chooseString(String oldest, String newest) {
        return newest == null || newest.isBlank() ? oldest : newest;
    }
}
