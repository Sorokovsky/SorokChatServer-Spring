package pro.sorokovsky.sorokchatserverspring.contract;

import java.util.Date;
import java.util.List;

public record GetChannelPayload(
        long id,
        Date createdAt,
        Date updatedAt,
        String name,
        String description,
        List<GetUserPayload> users
) {
}
