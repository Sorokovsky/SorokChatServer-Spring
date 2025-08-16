package sorokovsky.sorokchatserverspring.util;

import pro.sorokovsky.sorokchatserverspring.entity.ChannelEntity;
import pro.sorokovsky.sorokchatserverspring.model.ChannelModel;

import java.util.Date;
import java.util.List;

import static sorokovsky.sorokchatserverspring.util.MessagesUtil.getMessageEntity;
import static sorokovsky.sorokchatserverspring.util.MessagesUtil.getMessageModel;
import static sorokovsky.sorokchatserverspring.util.TimeUtil.NOW_INSTANT;
import static sorokovsky.sorokchatserverspring.util.UsersUtil.getUserEntity;
import static sorokovsky.sorokchatserverspring.util.UsersUtil.getUserModel;

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
}
