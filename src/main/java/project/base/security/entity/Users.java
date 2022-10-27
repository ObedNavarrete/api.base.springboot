package project.base.security.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import project.base.security.util.EntityBase;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
public class Users implements Serializable {
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    private Integer id;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_role",
    joinColumns = @JoinColumn(name = "users_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles = new ArrayList<>();

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private Boolean enabled;

    /*
    // Campos de auditoria
     */
    private Boolean pasive = false;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date modifiedAt;
    private Integer createdBy;
    private Integer modifiedBy;
    private String createdByIp;
    private String modifiedByIp;
}
