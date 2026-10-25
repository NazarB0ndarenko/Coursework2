package lt.viko.bondarenko.mappper;

import lt.viko.bondarenko.dto.user.CreateUserInfoDto;
import lt.viko.bondarenko.dto.user.UserRoleDto;
import lt.viko.bondarenko.entity.User;
import lt.viko.bondarenko.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User userInfoDtoToUser(CreateUserInfoDto userInfoDto) {
        return User.builder()
                .email(userInfoDto.getEmail())
                .firstName(userInfoDto.getFirstName())
                .lastName(userInfoDto.getLastName())
                .password(userInfoDto.getPassword())
                .role(Role.NOT_CONFIRMED)
                .build();
    }

    public UserRoleDto userToLoginDto(User user) {
        return new UserRoleDto(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }
}
