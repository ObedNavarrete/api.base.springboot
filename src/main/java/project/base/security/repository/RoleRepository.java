package project.base.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.base.security.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
    Role findByPasiveIsFalseAndName(String name);
}
