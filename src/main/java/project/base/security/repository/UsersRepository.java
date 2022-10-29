package project.base.security.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;
import project.base.security.entity.Users;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByEmail(String email);
    Users findByPasiveIsFalseAndEmail(String email);

    Users findByPasiveIsFalseAndEnabledIsTrueAndEmail(String email);

    Page<Users> findAllByPasiveIsFalse(Pageable pageable);

    @Query(value = "select id from users where pasive = false and enabled = true and email = ?1", nativeQuery = true)
    Integer findIdLogued(String email);

    Users findByPasiveIsFalseAndEnabledIsTrueAndPhone(String phone);
}
