package lt.viko.bondarenko.service.serviceimpl;

import lt.viko.bondarenko.dto.user.ChangePasswordDto;
import lt.viko.bondarenko.dto.user.CreateUserInfoDto;
import lt.viko.bondarenko.dto.user.UserRoleDto;
import lt.viko.bondarenko.entity.User;
import lt.viko.bondarenko.enums.Role;
import lt.viko.bondarenko.exception.AuthenticationRuntimeException;
import lt.viko.bondarenko.exception.EmailAlreadyInUseException;
import lt.viko.bondarenko.mappper.UserMapper;
import lt.viko.bondarenko.repository.UserRepository;
import lt.viko.bondarenko.util.SecurityUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private UserRoleDto userRoleDto;
    private CreateUserInfoDto createUserInfoDto;
    private SecurityUser securityUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setPassword("encodedPassword");
        testUser.setRole(Role.USER);

        userRoleDto = new UserRoleDto();
        userRoleDto.setId(1L);
        userRoleDto.setEmail("test@example.com");
        userRoleDto.setRole(Role.USER);

        createUserInfoDto = new CreateUserInfoDto();
        createUserInfoDto.setEmail("newuser@example.com");
        createUserInfoDto.setPassword("plainPassword");

        securityUser = new SecurityUser(userRoleDto);
    }

    @Test
    void loadUserByUsername_shouldReturnSecurityUserWhenUserExists() {
        String email = "test@example.com";
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(testUser));
        when(userMapper.userToLoginDto(testUser)).thenReturn(userRoleDto);

        UserDetails result = userService.loadUserByUsername(email);

        assertThat(result).isInstanceOf(SecurityUser.class);
        SecurityUser securityUser = (SecurityUser) result;
        assertThat(securityUser.getId()).isEqualTo(1L);
        verify(userRepository).findUserByEmail(email);
    }

    @Test
    void loadUserByUsername_shouldThrowExceptionWhenUserNotFound() {
        String email = "nonexistent@example.com";
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.loadUserByUsername(email))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("Username not found");
    }

    @Test
    void createUser_shouldSaveUserWhenEmailNotTaken() {
        when(userRepository.countUserByEmail(createUserInfoDto.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(createUserInfoDto.getPassword())).thenReturn("encodedPassword");
        when(userMapper.userInfoDtoToUser(createUserInfoDto)).thenReturn(testUser);

        userService.createUser(createUserInfoDto);

        verify(userRepository).countUserByEmail(createUserInfoDto.getEmail());
        verify(passwordEncoder).encode("plainPassword");
        verify(userRepository).save(testUser);
    }

    @Test
    void createUser_shouldThrowExceptionWhenEmailAlreadyExists() {
        when(userRepository.countUserByEmail(createUserInfoDto.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(createUserInfoDto))
                .isInstanceOf(EmailAlreadyInUseException.class)
                .hasMessage("This email already taken");

        verify(userRepository, never()).save(any());
    }

    @Test
    void saveUser_shouldEncodePasswordAndSaveUser() {
        String plainPassword = "plainPassword";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(plainPassword)).thenReturn(encodedPassword);
        when(userMapper.userInfoDtoToUser(createUserInfoDto)).thenReturn(testUser);

        userService.saveUser(createUserInfoDto);

        assertThat(createUserInfoDto.getPassword()).isEqualTo(encodedPassword);
        verify(passwordEncoder).encode(plainPassword);
        verify(userRepository).save(testUser);
    }

    @Test
    void userExists_shouldReturnTrueWhenUserExists() {
        String email = "test@example.com";
        when(userRepository.countUserByEmail(email)).thenReturn(true);

        boolean result = userService.userExists(email);

        assertThat(result).isTrue();
    }

    @Test
    void changeRoleByEmail_shouldUpdateUserRoleWhenUserExists() {
        String email = "test@example.com";
        Role newRole = Role.ADMIN;

        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(testUser));
        when(userRepository.save(testUser)).thenReturn(testUser);

        userService.changeRole(email, newRole);

        assertThat(testUser.getRole()).isEqualTo(newRole);
        verify(userRepository).save(testUser);
    }

    @Test
    void changeRoleByUserId_shouldUpdateUserRoleWhenUserExists() {
        long userId = 1L;
        Role newRole = Role.MODERATOR;

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(userRepository.save(testUser)).thenReturn(testUser);

        userService.changeRole(userId, newRole);

        assertThat(testUser.getRole()).isEqualTo(newRole);
        verify(userRepository).save(testUser);
    }

    @Test
    void changeRoleByUserId_shouldThrowExceptionWhenUserNotFound() {
        long userId = 999L;
        Role newRole = Role.ADMIN;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.changeRole(userId, newRole))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("Username not found");
    }

    @Test
    void changePassword_shouldUpdatePasswordWhenOldPasswordMatches() {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto("oldPassword", "newPassword");

        String email = "test@example.com";
        String newEncodedPassword = "newEncodedPassword";

        try (MockedStatic<SecurityContextHolder> mockedStatic = mockStatic(SecurityContextHolder.class)) {
            mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn(email);
            when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(testUser));
            when(passwordEncoder.matches("oldPassword", testUser.getPassword())).thenReturn(true);
            when(passwordEncoder.encode("newPassword")).thenReturn(newEncodedPassword);

            userService.changePassword(changePasswordDto);

            assertThat(testUser.getPassword()).isEqualTo(newEncodedPassword);
            verify(userRepository).save(testUser);
        }
    }

    @Test
    void changePassword_shouldThrowExceptionWhenOldPasswordDoesNotMatch() {
        ChangePasswordDto changePasswordDto = new ChangePasswordDto("wrongPassword", "newPassword");

        String email = "test@example.com";

        try (MockedStatic<SecurityContextHolder> mockedStatic = mockStatic(SecurityContextHolder.class)) {
            mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn(email);
            when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(testUser));
            when(passwordEncoder.matches("wrongPassword", testUser.getPassword())).thenReturn(false);

            assertThatThrownBy(() -> userService.changePassword(changePasswordDto))
                    .isInstanceOf(BadCredentialsException.class)
                    .hasMessage("Bad credentials");

            verify(passwordEncoder, never()).encode(anyString());
        }
    }

    @Test
    void getUserIdByEmail_shouldReturnUserIdWhenUserExists() {
        String email = "test@example.com";
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(testUser));

        long result = userService.getUserIdByEmail(email);

        assertThat(result).isEqualTo(1L);
    }

    @Test
    void getUserIdByEmail_shouldThrowExceptionWhenUserNotFound() {
        String email = "nonexistent@example.com";
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserIdByEmail(email))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("Username not found");
    }

    @Test
    void getLoggedInUserId_shouldReturnUserIdWhenUserAuthenticated() {
        try (MockedStatic<SecurityContextHolder> mockedStatic = mockStatic(SecurityContextHolder.class)) {
            mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.isAuthenticated()).thenReturn(true);
            when(authentication.getPrincipal()).thenReturn(securityUser);

            Long result = userService.getLoggedInUserId();

            assertThat(result).isEqualTo(1L);
        }
    }

    @Test
    void getLoggedInUserId_shouldThrowExceptionWhenAuthenticationIsNull() {
        try (MockedStatic<SecurityContextHolder> mockedStatic = mockStatic(SecurityContextHolder.class)) {
            mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(null);

            assertThatThrownBy(() -> userService.getLoggedInUserId())
                    .isInstanceOf(AuthenticationRuntimeException.class);
        }
    }

    @Test
    void validateLoggedInUser_shouldReturnUserWhenUserExists() {
        try (MockedStatic<SecurityContextHolder> mockedStatic = mockStatic(SecurityContextHolder.class)) {
            mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.isAuthenticated()).thenReturn(true);
            when(authentication.getPrincipal()).thenReturn(securityUser);
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

            User result = userService.validateLoggedInUser();

            assertThat(result).isEqualTo(testUser);
        }
    }

    @Test
    void validateLoggedInUser_shouldThrowExceptionWhenUserNotFound() {
        try (MockedStatic<SecurityContextHolder> mockedStatic = mockStatic(SecurityContextHolder.class)) {
            mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.isAuthenticated()).thenReturn(true);
            when(authentication.getPrincipal()).thenReturn(securityUser);
            when(userRepository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> userService.validateLoggedInUser())
                    .isInstanceOf(AuthenticationRuntimeException.class);
        }
    }

    @Test
    void hasRole_shouldReturnTrueWhenUserHasRole() {
        String role = "ROLE_ADMIN";
        GrantedAuthority authority = new SimpleGrantedAuthority(role);
        Collection<GrantedAuthority> authorities = Collections.singletonList(authority);

        try (MockedStatic<SecurityContextHolder> mockedStatic = mockStatic(SecurityContextHolder.class)) {
            mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getAuthorities()).thenReturn((Collection) authorities);

            boolean result = userService.hasRole(role);

            assertThat(result).isTrue();
        }
    }

    @Test
    void hasRole_shouldReturnFalseWhenAuthenticationIsNull() {
        try (MockedStatic<SecurityContextHolder> mockedStatic = mockStatic(SecurityContextHolder.class)) {
            mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(null);

            boolean result = userService.hasRole("ROLE_ADMIN");

            assertThat(result).isFalse();
        }
    }


    @Test
    void getIdentity_shouldReturnUserRoleDtoWhenUserAuthenticated() {
        try (MockedStatic<SecurityContextHolder> mockedStatic = mockStatic(SecurityContextHolder.class)) {
            mockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.isAuthenticated()).thenReturn(true);
            when(authentication.getPrincipal()).thenReturn(securityUser);
            when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
            when(userMapper.userToLoginDto(testUser)).thenReturn(userRoleDto);

            UserRoleDto result = userService.getIdentity();

            assertThat(result).isEqualTo(userRoleDto);
            verify(userRepository, times(2)).findById(1L);
        }
    }
}