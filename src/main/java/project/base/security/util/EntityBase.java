package project.base.security.util;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import project.base.security.dto.UserDTO;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class EntityBase {
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date modifiedAt;

    private Integer createdBy;
    private UserDTO createdByUser;
    private Integer modifiedBy;
    private UserDTO modifiedByUser;
    private String createdByIp;
    private String modifiedByIp;
    private Boolean pasive = false;
}
