package project.base.security.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserDTO {
    private Integer id;

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Boolean enabled;

    private Collection<RoleDTO> roles = new ArrayList<>();
}
