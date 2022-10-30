package project.base.security.service;

import project.base.security.dto.ResponseDTO;
import project.base.security.dto.UserDTO;
import project.base.security.dto.UsersWithPassDTO;
import project.base.security.entity.Role;

import java.util.Map;

public interface UsersService {
    ResponseDTO save(UsersWithPassDTO user);

    ResponseDTO saveSuperAdmin(UsersWithPassDTO user);

    ResponseDTO saveAdmin(UsersWithPassDTO user);

    ResponseDTO saveCustomer(UsersWithPassDTO user);

    ResponseDTO saveSubCustomer(UsersWithPassDTO user);

    ResponseDTO getUserById(Integer id);

    UserDTO getUserByEmail(String email);

    UserDTO getUserByPhone(String phone);

    ResponseDTO getAllUsers(int page, int size);

    ResponseDTO updateUser(Integer id, UserDTO user);

    ResponseDTO deleteUser(Integer id);

    /**
     * By: Obed Navarrete
     * Date: 29/10/2022
     * This methods is used for the role save and assign to the user
     * @param role to be saved
     * @return the persisted entity of the role saved
     */

    ResponseDTO saveRole(Role role);

    Map<String, String> addRoleToUser(String email, String roleName);

    Map<String, String> deleteRoleFromUser(String email, String roleName);
}
