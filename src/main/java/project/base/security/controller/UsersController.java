package project.base.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import project.base.security.dto.RoleUserDTO;
import project.base.security.dto.UserDTO;
import project.base.security.dto.UsersWithPassDTO;
import project.base.security.entity.Role;
import project.base.security.service.UsersService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor @Slf4j
public class UsersController {
    private final UsersService usersService;

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<?> getUsers(
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        return ResponseEntity.ok(usersService.getAllUsers(page, size));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(usersService.getUserById(id));
    }

    @GetMapping("/users/byEmail/{email}/")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(usersService.getUserByEmail(email));
    }

    @GetMapping("/users/byPhone/{phone}")
    public ResponseEntity<?> getUserByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(usersService.getUserByPhone(phone));
    }

    @PostMapping("/users/save")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UsersWithPassDTO user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/save").toUriString());
        return ResponseEntity.created(uri).body(usersService.save(user));
    }

    @PostMapping("/users/saveSuperAdmin")
    public ResponseEntity<?> saveSuperAdmin(@Valid @RequestBody UsersWithPassDTO user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/saveSuperAdmin").toUriString());
        return ResponseEntity.created(uri).body(usersService.saveSuperAdmin(user));
    }

    @PostMapping("/users/saveAdmin")
    public ResponseEntity<?> saveAdmin(@Valid @RequestBody UsersWithPassDTO user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/saveAdmin").toUriString());
        return ResponseEntity.created(uri).body(usersService.saveAdmin(user));
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("/users/saveSubCustomer")
    public ResponseEntity<?> saveSubCustomer(@Valid @RequestBody UsersWithPassDTO user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/saveSubCustomer").toUriString());
        return ResponseEntity.created(uri).body(usersService.saveSubCustomer(user));
    }

    @PutMapping("/users/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserDTO user) {
        return ResponseEntity.ok(usersService.updateUser(id, user));
    }

    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        return ResponseEntity.ok(usersService.deleteUser(id));
    }

    /*
     * @RelativeToRole
     * @Path: src\main\java\project\base\security\entity\Role.java
     */
    @PostMapping("/role/save")
    public ResponseEntity<?> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(usersService.saveRole(role));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    @PostMapping("/role/addToUser")
    public ResponseEntity<?> addRoleToUser(@Valid @RequestBody RoleUserDTO form) {
        return ResponseEntity.ok().body(usersService.addRoleToUser(form.getEmail(), form.getRolName()));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    @DeleteMapping("/role/deleteFromUser")
    public ResponseEntity<?> deleteRoleFromUser(@Valid @RequestBody RoleUserDTO form) {
        return ResponseEntity.ok().body(usersService.deleteRoleFromUser(form.getEmail(), form.getRolName()));
    }
}
