package project.base.security.dto;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UsersWithPassDTO {
    private Integer id;

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private Boolean enabled;
}
