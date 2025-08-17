package sorokovsky.sorokchatserverspring.util;

import pro.sorokovsky.sorokchatserverspring.contract.NewMessagePayload;
import pro.sorokovsky.sorokchatserverspring.contract.UpdateMessagePayload;
import pro.sorokovsky.sorokchatserverspring.entity.MessageEntity;
import pro.sorokovsky.sorokchatserverspring.model.MessageModel;

import java.util.Date;

import static sorokovsky.sorokchatserverspring.util.TimeUtil.NOW_INSTANT;
import static sorokovsky.sorokchatserverspring.util.UsersUtil.getUserEntity;
import static sorokovsky.sorokchatserverspring.util.UsersUtil.getUserModel;

public class MessagesUtil {
    public static MessageEntity getMessageEntity() {
        return MessageEntity
                .builder()
                .id(1L)
                .createdAt(Date.from(NOW_INSTANT))
                .updatedAt(Date.from(NOW_INSTANT))
                .text("Круте повідомлення")
                .author(getUserEntity())
                .build();
    }

    public static MessageModel getMessageModel() {
        return MessageModel
                .builder()
                .id(1L)
                .createdAt(Date.from(NOW_INSTANT))
                .updatedAt(Date.from(NOW_INSTANT))
                .text("Круте повідомлення")
                .author(getUserModel())
                .build();
    }

    public static UpdateMessagePayload getNewStateWithText() {
        return new UpdateMessagePayload("Нове круте повідомлення");
    }

    public static MessageEntity getMergedWithText() {
        final var newState = getNewStateWithText();
        final var message = getMessageEntity();
        message.setText(newState.text());
        return message;
    }

    public static MessageModel getMergedModelWithText() {
        final var newState = getNewStateWithText();
        final var message = getMessageModel();
        message.setText(newState.text());
        return message;
    }

    public static UpdateMessagePayload getNewStateWithNullText() {
        return new UpdateMessagePayload(null);
    }

    public static MessageEntity getMergedWithNullText() {
        final var oldState = getMessageEntity();
        final var message = getMessageEntity();
        message.setText(oldState.getText());
        return message;
    }

    public static UpdateMessagePayload getNewStateWithEmptyText() {
        return new UpdateMessagePayload("");
    }

    public static MessageEntity getMergedWithEmptyText() {
        final var oldState = getMessageEntity();
        final var message = getMessageEntity();
        message.setText(oldState.getText());
        return message;
    }

    public static NewMessagePayload getNewMessagePayload() {
        return new NewMessagePayload("Круте повідомлення");
    }
}
