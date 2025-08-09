package pro.sorokovsky.sorokchatserverspring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModel {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
}
