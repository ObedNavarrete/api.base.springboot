package project.base.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.base.security.dto.UsersWithPassDTO;
import project.base.security.entity.Role;
import project.base.security.service.UsersService;

import java.util.ArrayList;

@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
        System.out.println("SecurityApplication started...");
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    CommandLineRunner runner(UsersService usersService) {
        return args -> {
            usersService.saveRole(new Role(null, "USER", false));
            usersService.saveRole(new Role(null, "MANAGER", false));
            usersService.saveRole(new Role(null, "ADMIN", false));
            usersService.saveRole(new Role(null, "UPER_ADMIN", false));

            usersService.save(
                    new UsersWithPassDTO(null,
                            "Obed", "Navarrete", "88776655", "obed@mail.com",
                            "1234",true));

            usersService.save(
                    new UsersWithPassDTO(null,
                            "Isaí", "Navarrete", "88776644", "isai@mail.com",
                            "1234",true));

            usersService.save(
                    new UsersWithPassDTO(null,
                            "Edgard", "Navarrete", "88776633", "edgard@mail.com",
                            "1234",true));

            usersService.addRoleToUser("obed@mail.com", "USER");
            usersService.addRoleToUser("obed@mail.com", "MANAGER");
            usersService.addRoleToUser("obed@mail.com", "ADMIN");
            usersService.addRoleToUser("isai@mail.com", "MANAGER");
            usersService.addRoleToUser("edgard@mail.com", "ADMIN");
        };
    }*/

}
