package project.base.security.dto;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RoleUserDTO {
    private String email;
    private String rolName;
}
