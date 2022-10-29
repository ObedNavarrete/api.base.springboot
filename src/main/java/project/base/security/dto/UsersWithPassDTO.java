package project.base.security.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UsersWithPassDTO {
    private Integer id;

    @NotNull(message = "First name is required")
    private String firstName;
    @NotNull(message = "Last name is required")
    private String lastName;
    @NotNull(message = "Phone is required")
    @Pattern(regexp = "^(\\+?\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$", message = "Phone number is not valid")
    @Length(min = 10, max = 10, message = "Phone number must be 10 digits")
    private String phone;
    @NotNull(message = "Email is required")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email is not valid")
    private String email;
    @NotNull(message = "Password is required")
    private String password;
    private Boolean enabled;
}
