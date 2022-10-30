package project.base.security.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RoleUserDTO {
    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Role is required")
    private String rolName;
}
