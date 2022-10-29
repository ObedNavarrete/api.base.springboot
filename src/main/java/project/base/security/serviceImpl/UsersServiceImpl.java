package project.base.security.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.base.security.dto.PageResponse;
import project.base.security.dto.ResponseDTO;
import project.base.security.dto.UserDTO;
import project.base.security.dto.UsersWithPassDTO;
import project.base.security.entity.Role;
import project.base.security.entity.Users;
import project.base.security.mapper.UsersMapper;
import project.base.security.repository.RoleRepository;
import project.base.security.repository.UsersRepository;
import project.base.security.service.UsersService;
import project.base.security.util.UtilityBase;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UsersServiceImpl extends UtilityBase implements UsersService, UserDetailsService {
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsersMapper usersMapper;

    /**
     * By: Obed Navarrete
     * Date: 29/10/2022
     * This method is used for the authentication of the user
     * @param email or phone of the user to be authenticated
     * @return the persisted entity of the user authenticated
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = usersRepository.findByPasiveIsFalseAndEnabledIsTrueAndEmail(email);

        if (users == null) {
            log.error("Email not found in the database");
            users = usersRepository.findByPasiveIsFalseAndEnabledIsTrueAndPhone(email);
            if (users == null) {
                log.error("Phone not found in the database");
                throw new UsernameNotFoundException("Email or Phone not found in the database");
            }
        }

        log.info("User found in the database: {}", email);
        Collection<SimpleGrantedAuthority> authorities = users.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());


        return new org.springframework.security.core.userdetails
                .User(users.getEmail(), users.getPassword(), authorities);
    }

    @Override
    public ResponseDTO save(UsersWithPassDTO user) {
        log.info("save new user {} to the database ", user.getEmail());

        ResponseDTO response = new ResponseDTO();
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Users usersEntity = usersMapper.withPassToEtity(user);
            usersEntity.setEnabled(true);
            usersEntity.setCreatedAt(new Date());
            usersEntity.setCreatedByIp("localhost");
            usersRepository.save(usersEntity);

            response.setStatus("200");
            response.setMessage("success");
            response.setComment("User saved successfully");
            response.setData(usersMapper.toDTO(usersEntity));
            return response;
        } catch (Exception e) {
            response.setStatus("500");
            response.setMessage("error");
            response.setComment("User save failed, comment: " + e.getMessage());
            return response;
        }
    }

    @Override
    public ResponseDTO saveSuperAdmin(UsersWithPassDTO user) {
        log.info("save new user {} to the database ", user.getEmail());

        if ((user.getEmail().isEmpty() || user.getEmail() == null) && (user.getPhone().isEmpty() || user.getPhone() == null)) {
            return new ResponseDTO("500", "error", "User save failed, comment: Email or Phone is required", null);
        }

        ResponseDTO response = new ResponseDTO();
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Users usersEntity = usersMapper.withPassToEtity(user);
            usersEntity.setEnabled(true);
            usersEntity.setCreatedAt(new Date());
            usersEntity.setCreatedByIp(this.createdByIp());
            Users nu = usersRepository.save(usersEntity);
            nu.getRoles().add(roleRepository.findByName("ROLE_SUPER_ADMIN"));

            response.setStatus("200");
            response.setMessage("success");
            response.setComment("User saved successfully");
            response.setData(usersMapper.toDTO(usersEntity));
            return response;
        } catch (Exception e) {
            log.error("User save failed, comment: " + e.getMessage());
            response.setStatus("500");
            response.setMessage("error");
            response.setComment("User save failed, comment: " + e.getMessage());
            return response;
        }
    }

    @Override
    public ResponseDTO saveAdmin(UsersWithPassDTO user) {
        log.info("save new user {} to the database ", user.getEmail());

        if ((user.getEmail().isEmpty() || user.getEmail() == null) && (user.getPhone().isEmpty() || user.getPhone() == null)) {
            return new ResponseDTO("500", "error", "User save failed, comment: Email or Phone is required", null);
        }

        ResponseDTO response = new ResponseDTO();
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Users usersEntity = usersMapper.withPassToEtity(user);
            usersEntity.setEnabled(true);
            usersEntity.setCreatedAt(new Date());
            usersEntity.setCreatedByIp(this.createdByIp());
            Users nu = usersRepository.save(usersEntity);
            nu.getRoles().add(roleRepository.findByName("ROLE_ADMIN"));

            response.setStatus("200");
            response.setMessage("success");
            response.setComment("User saved successfully");
            response.setData(usersMapper.toDTO(usersEntity));

            log.info("User saved successfully");
            return response;
        } catch (Exception e) {
            log.error("User save failed, comment: " + e.getMessage());
            response.setStatus("500");
            response.setMessage("error");
            response.setComment("User save failed, comment: " + e.getMessage());
            return response;
        }
    }

    @Override
    public ResponseDTO saveCustomer(UsersWithPassDTO user) {
        log.info("save new user {} to the database ", user.getEmail());

        if ((user.getEmail().isEmpty() || user.getEmail() == null) && (user.getPhone().isEmpty() || user.getPhone() == null)) {
            return new ResponseDTO("500", "error", "User save failed, comment: Email or Phone is required", null);
        }

        ResponseDTO response = new ResponseDTO();
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Users usersEntity = usersMapper.withPassToEtity(user);
            usersEntity.setEnabled(true);
            usersEntity.setCreatedAt(new Date());
            usersEntity.setCreatedByIp(this.createdByIp());
            Users nu = usersRepository.save(usersEntity);
            nu.getRoles().add(roleRepository.findByName("ROLE_CUSTOMER"));

            response.setStatus("200");
            response.setMessage("success");
            response.setComment("User saved successfully");
            response.setData(usersMapper.toDTO(usersEntity));
            return response;
        } catch (Exception e) {
            log.error("User save failed, comment: " + e.getMessage());
            response.setStatus("500");
            response.setMessage("error");
            response.setComment("User save failed, comment: " + e.getMessage());
            return response;
        }
    }

    @Override
    public ResponseDTO saveSubCustomer(UsersWithPassDTO user) {
        log.info("save new user {} to the database ", user.getEmail());

        if ((user.getEmail().isEmpty() || user.getEmail() == null) && (user.getPhone().isEmpty() || user.getPhone() == null)) {
            return new ResponseDTO("500", "error", "User save failed, comment: Email or Phone is required", null);
        }

        ResponseDTO response = new ResponseDTO();
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Users usersEntity = usersMapper.withPassToEtity(user);
            usersEntity.setEnabled(true);
            usersEntity.setCreatedAt(new Date());
            usersEntity.setCreatedByIp(this.createdByIp());
            Users nu = usersRepository.save(usersEntity);
            nu.getRoles().add(roleRepository.findByName("ROLE_SUB_CUSTOMER"));

            response.setStatus("200");
            response.setMessage("success");
            response.setComment("User saved successfully");
            response.setData(usersMapper.toDTO(usersEntity));
            return response;
        } catch (Exception e) {
            response.setStatus("500");
            response.setMessage("error");
            response.setComment("User save failed, comment: " + e.getMessage());
            return response;
        }
    }



    @Override
    public ResponseDTO getUserById(Integer id) {
        log.info("get user by id {} ", id);
        Users user = usersRepository.findById(id).orElse(null);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found");
        }

        return new ResponseDTO("200", "success", "User found", usersMapper.toDTO(user));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        log.info("get user by email {} ", email);
        Users user = usersRepository.findByPasiveIsFalseAndEnabledIsTrueAndEmail(email);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found");
        }

        return usersMapper.toDTO(user);
    }

    @Override
    public UserDTO getUserByPhone(String phone) {
        log.info("get user by phone {} ", phone);
        Users user = usersRepository.findByPasiveIsFalseAndEnabledIsTrueAndPhone(phone);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found");
        }

        return usersMapper.toDTO(user);
    }

    @Override
    public ResponseDTO getAllUsers(int page, int size) {
        log.info("get all users");
        PageResponse pageResponse = new PageResponse();
        page = page - 1;

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        Page<Users> users = usersRepository.findAllByPasiveIsFalse(pageable);
        List<Users> usersList = users.getContent();

        if (usersList.isEmpty()) {
            log.error("Users not found in the database");
            return new ResponseDTO("404", "error", "Users not found", null);
        }

        List<UserDTO> userDTOList = usersList.stream().map(usersMapper::toDTO).collect(Collectors.toList());

        pageResponse.setContent(userDTOList);
        pageResponse.setTotalElements(users.getTotalElements());
        pageResponse.setTotalPages(users.getTotalPages());
        pageResponse.setPage(users.getNumber() + 1);
        pageResponse.setSize(users.getSize());
        pageResponse.setLast(users.isLast());

        return new ResponseDTO("200", "success", "Users found", pageResponse);
    }

    @Override
    public ResponseDTO updateUser(Integer id, UserDTO user) {
        log.info("update user {} to the database ", user.toString());
        ResponseDTO response = new ResponseDTO();

        Users usersEntity = usersRepository.findById(id).orElse(null);
        if (usersEntity == null) {
            log.error("User not found in the database");
            return new ResponseDTO("404", "error", "User not found", null);
        }

        try {
            usersEntity.setFirstName(user.getFirstName());
            usersEntity.setLastName(user.getLastName());
            usersEntity.setPhone(user.getPhone());
            usersEntity.setEmail(user.getEmail());
            usersEntity.setModifiedAt(this.modifiedAt());
            usersEntity.setModifiedByIp(this.modifiedByIp());
            usersEntity.setModifiedBy(this.modifiedBy());
            usersEntity.setPasive(false);
            usersEntity.setEnabled(true);

            usersRepository.save(usersEntity);
            response.setStatus("200");
            response.setMessage("success");
            response.setComment("User updated successfully");
            response.setData(null);
            log.info("User updated successfully");
            return response;
        } catch (Exception e) {
            response.setStatus("500");
            response.setMessage("error");
            response.setComment("User update failed, comment: " + e.getMessage());
            return response;
        }
    }

    @Override
    public ResponseDTO deleteUser(Integer id) {
        log.info("delete user by id {} ", id);
        ResponseDTO response = new ResponseDTO();

        Users usersEntity = usersRepository.findById(id).orElse(null);
        if (usersEntity == null) {
            log.error("User not found in the database");
            return new ResponseDTO("404", "error", "User not found", null);
        }

        try {
            usersEntity.setModifiedAt(this.modifiedAt());
            usersEntity.setModifiedByIp(this.modifiedByIp());
            usersEntity.setModifiedBy(this.modifiedBy());
            usersEntity.setPasive(true);
            usersEntity.setEnabled(false);

            log.info("Deleting user with email: {}", usersEntity.getEmail());

            usersRepository.save(usersEntity);
            response.setStatus("200");
            response.setMessage("success");
            response.setComment("User deleted successfully");
            response.setData(null);
            log.info("User deleted successfully");
            return response;
        } catch (Exception e) {
            response.setStatus("500");
            response.setMessage("error");
            response.setComment("User delete failed, comment: " + e.getMessage());
            return response;
        }
    }

    /**
     * By: Obed Navarrete
     * Date: 29/10/2022
     * This methods is used for the role save and assign to the user
     * @param role to be saved
     * @return the persisted entity of the role saved
     */
    @Override
    public ResponseDTO saveRole(Role role) {
        log.info("save new role {} to the database ", role.getName());
        ResponseDTO response = new ResponseDTO();
        try {
            role.setPasive(false);
            roleRepository.save(role);
            response.setStatus("200");
            response.setMessage("success");
            response.setComment("Role saved successfully");
            response.setData(role);
            return response;
        } catch (Exception e) {
            response.setStatus("500");
            response.setMessage("error");
            response.setComment("Role save failed, comment: " + e.getMessage());
            return response;
        }
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("add role {} to user {} ", roleName, email);

        Users users = usersRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);

        users.getRoles().add(role);
    }
}
