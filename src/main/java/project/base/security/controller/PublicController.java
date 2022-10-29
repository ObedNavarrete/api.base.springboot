package project.base.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import project.base.security.dto.UsersWithPassDTO;
import project.base.security.service.UsersService;

import java.net.URI;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
@Slf4j
public class PublicController {
    private final UsersService usersService;

    @PostMapping("/users/saveCustomer")
    public ResponseEntity<?> saveCustomer(@RequestBody UsersWithPassDTO user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/public/users/saveCustomer").toUriString());
        return ResponseEntity.created(uri).body(usersService.saveCustomer(user));
    }
}
