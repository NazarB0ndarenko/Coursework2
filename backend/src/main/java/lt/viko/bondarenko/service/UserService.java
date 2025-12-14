package lt.viko.bondarenko.service;

import lt.viko.bondarenko.dto.user.CreateUserInfoDto;
import lt.viko.bondarenko.dto.user.UserRoleDto;
import lt.viko.bondarenko.entity.User;
import lt.viko.bondarenko.dto.user.ChangePasswordDto;
import lt.viko.bondarenko.enums.Role;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

public interface UserService extends UserDetailsService {

     void createUser(CreateUserInfoDto createUserInfoDto);

     @Transactional
     void saveUser(CreateUserInfoDto createUserInfoDto);

     boolean userExists(String email);

     Long getLoggedInUserId();

     UserRoleDto getIdentity();

     User validateLoggedInUser();

     boolean hasRole(String role);

     void changeRole(String email, Role role);

     void changeRole(long userId, Role role);

     void changePassword(ChangePasswordDto changePasswordDto);

     long getUserIdByEmail(String email);
}
