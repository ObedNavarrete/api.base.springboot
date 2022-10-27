package project.base.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import project.base.security.dto.RoleUserDTO;
import project.base.security.dto.UsersWithPassDTO;
import project.base.security.entity.Role;
import project.base.security.service.UsersService;

import java.net.URI;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor @Slf4j
public class UsersController {
    private final UsersService usersService;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        return ResponseEntity.ok(usersService.getUsers(page, size));
    }

    @PostMapping("/users/save")
    public ResponseEntity<?> saveUser(@RequestBody UsersWithPassDTO user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/save").toUriString());
        return ResponseEntity.created(uri).body(usersService.save(user));
    }

    @PostMapping("/role/save")
    public ResponseEntity<?> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(usersService.saveRole(role));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/role/addToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleUserDTO form) {
        usersService.addRoleToUser(form.getEmail(), form.getRolName());
        return ResponseEntity.ok().build();
    }
}
