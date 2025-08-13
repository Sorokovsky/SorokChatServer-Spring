package pro.sorokovsky.sorokchatserverspring.contract;

import jakarta.validation.constraints.NotNull;

public record NewChannelPayload(@NotNull String name, String description) {
}
