package project.base.security.service;

import project.base.security.dto.ResponseDTO;
import project.base.security.dto.UserDTO;
import project.base.security.dto.UsersWithPassDTO;
import project.base.security.entity.Role;

public interface UsersService {
    ResponseDTO save(UsersWithPassDTO user);

    ResponseDTO saveRole(Role role);

    void addRoleToUser(String email, String roleName);

    UserDTO getUser(String email);

    ResponseDTO getUsers(int page, int size);
}
