package project.base.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "role")
@Data @AllArgsConstructor @NoArgsConstructor
@SequenceGenerator(name = "role_id_seq", sequenceName = "role_id_seq", allocationSize = 1)
public class Role implements Serializable {
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    private Integer id;
    private String name;
    private Boolean pasive = false;
}
