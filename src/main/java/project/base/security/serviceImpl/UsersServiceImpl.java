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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = usersRepository.findByPasiveIsFalseAndEnabledIsTrueAndAndEmail(email);

        if (users == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found");
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

    @Override
    public UserDTO getUser(String email) {
        log.info("get user by email {} ", email);
        Users user = usersRepository.findByEmail(email);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found");
        }

        return usersMapper.toDTO(user);
    }

    @Override
    public ResponseDTO getUsers(int page, int size) {
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
}
