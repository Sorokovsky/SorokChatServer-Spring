package pro.sorokovsky.sorokchatserverspring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelModel {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private List<UserModel> users;
    private List<MessageModel> messages;
    private String name;
    private String description;
}
