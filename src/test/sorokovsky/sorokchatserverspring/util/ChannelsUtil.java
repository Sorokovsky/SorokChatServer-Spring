package sorokovsky.sorokchatserverspring.util;

import pro.sorokovsky.sorokchatserverspring.contract.GetChannelPayload;
import pro.sorokovsky.sorokchatserverspring.contract.NewChannelPayload;
import pro.sorokovsky.sorokchatserverspring.contract.UpdateChannelPayload;
import pro.sorokovsky.sorokchatserverspring.entity.ChannelEntity;
import pro.sorokovsky.sorokchatserverspring.model.ChannelModel;

import java.util.Date;
import java.util.List;

import static sorokovsky.sorokchatserverspring.util.MessagesUtil.getMessageEntity;
import static sorokovsky.sorokchatserverspring.util.MessagesUtil.getMessageModel;
import static sorokovsky.sorokchatserverspring.util.TimeUtil.NOW_INSTANT;
import static sorokovsky.sorokchatserverspring.util.UsersUtil.*;

public class ChannelsUtil {
    public static ChannelEntity getChannelEntity() {
        return ChannelEntity
                .builder()
                .id(1L)
                .createdAt(Date.from(NOW_INSTANT))
                .updatedAt(Date.from(NOW_INSTANT))
                .name("Крутий чат")
                .description("Дуже крутий чат")
                .users(List.of(getUserEntity()))
                .messages(List.of(getMessageEntity()))
                .build();
    }

    public static ChannelModel getChannelModel() {
        return ChannelModel
                .builder()
                .id(1L)
                .createdAt(Date.from(NOW_INSTANT))
                .updatedAt(Date.from(NOW_INSTANT))
                .name("Крутий чат")
                .description("Дуже крутий чат")
                .users(List.of(getUserModel()))
                .messages(List.of(getMessageModel()))
                .build();
    }

    public static ChannelModel getChannelWithoutUsers() {
        return ChannelModel
                .builder()
                .id(1L)
                .createdAt(Date.from(NOW_INSTANT))
                .updatedAt(Date.from(NOW_INSTANT))
                .name("Крутий чат")
                .description("Дуже крутий чат")
                .users(List.of())
                .messages(List.of(getMessageModel()))
                .build();
    }

    public static ChannelEntity getChannelEntityWithoutUsers() {
        return ChannelEntity
                .builder()
                .id(1L)
                .createdAt(Date.from(NOW_INSTANT))
                .updatedAt(Date.from(NOW_INSTANT))
                .name("Крутий чат")
                .description("Дуже крутий чат")
                .users(List.of())
                .messages(List.of(getMessageEntity()))
                .build();
    }

    public static NewChannelPayload getNewChannelPayload() {
        return new NewChannelPayload("Крутий чат", "Дуже крутий чат");
    }

    public static GetChannelPayload getChannelPayload() {
        return new GetChannelPayload(
                1L,
                Date.from(NOW_INSTANT),
                Date.from(NOW_INSTANT),
                "Крутий чат",
                "Дуже крутий чат",
                List.of(getUserPayload())
        );
    }

    public static UpdateChannelPayload getUpdateChannelWithDataPayload() {
        return new UpdateChannelPayload("Мій чат", "Мій крутий чат");
    }

    public static ChannelModel getUpdatedChannel() {
        return ChannelModel
                .builder()
                .id(1L)
                .createdAt(Date.from(NOW_INSTANT))
                .updatedAt(Date.from(NOW_INSTANT))
                .name("Мій чат")
                .description("Мій крутий чат")
                .users(List.of(getUserModel()))
                .messages(List.of(getMessageModel()))
                .build();
    }

    public static ChannelEntity getUpdatedChannelEntity() {
        return ChannelEntity
                .builder()
                .id(1L)
                .createdAt(Date.from(NOW_INSTANT))
                .updatedAt(Date.from(NOW_INSTANT))
                .name("Мій чат")
                .description("Мій крутий чат")
                .users(List.of(getUserEntity()))
                .messages(List.of(getMessageEntity()))
                .build();
    }

    public static UpdateChannelPayload getUpdateChannelWithNotDataPayload() {
        return new UpdateChannelPayload("", null);
    }
}
