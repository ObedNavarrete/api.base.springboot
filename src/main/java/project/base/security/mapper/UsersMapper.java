package project.base.security.mapper;

import org.mapstruct.Mapper;
import project.base.security.dto.UserDTO;
import project.base.security.dto.UsersWithPassDTO;
import project.base.security.entity.Users;

@Mapper(componentModel = "spring")
public interface UsersMapper {
    Users toEntity(UserDTO userDTO);
    UserDTO toDTO(Users users);

    Users withPassToEtity(UsersWithPassDTO usersWithPassDTO);
}
