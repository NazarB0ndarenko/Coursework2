package lt.viko.bondarenko.service.serviceimpl;

import lt.viko.bondarenko.dto.user.ChangePasswordDto;
import lt.viko.bondarenko.dto.user.CreateUserInfoDto;
import lt.viko.bondarenko.dto.user.UserRoleDto;
import lt.viko.bondarenko.entity.User;
import lt.viko.bondarenko.enums.Role;
import lt.viko.bondarenko.exception.AuthenticationRuntimeException;
import lt.viko.bondarenko.exception.EmailAlreadyInUseException;
import lt.viko.bondarenko.mappper.UserMapper;
import lt.viko.bondarenko.util.SecurityUser;
import lt.viko.bondarenko.repository.UserRepository;
import lt.viko.bondarenko.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        log.info("Loading user by email: {}", email);

        UserRoleDto userRoleDto = userRepository.findUserByEmail(email)
                .map(userMapper::userToLoginDto)
                .orElseThrow(() -> {
                    log.error("User with email: {} was not found", email);
                    return new UsernameNotFoundException("Username not found");
                });

        log.info("User with email: {} is found", email);

        return new SecurityUser(userRoleDto);
    }

    @Override
    public void createUser(CreateUserInfoDto createUserInfoDto){
        if (userExists(createUserInfoDto.getEmail())) {
            log.error("Email {} is already taken", createUserInfoDto.getEmail());

            throw new EmailAlreadyInUseException("This email already taken");
        }

        saveUser(createUserInfoDto);
    }

    @Transactional
    @Override
    public void saveUser(CreateUserInfoDto createUserInfoDto) {
        log.info("Creating user with email: {}", createUserInfoDto.getEmail());
        createUserInfoDto.setPassword(passwordEncoder.encode(createUserInfoDto.getPassword()));
        User user = userMapper.userInfoDtoToUser(createUserInfoDto);

        log.info("Saving user with email: {} to database", user.getEmail());
        userRepository.save(user);
    }

    @Override
    public boolean userExists(String email) {
        log.info("checking existence of the user with email: {}", email);
        return userRepository.countUserByEmail(email);
    }

    @Transactional
    @Override
    public void changeRole(String email, Role role) {
        log.info("changing role for user: {}, now his role is: {}", email, role);

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> {
            return new UsernameNotFoundException("Username not found");
        });

        user.setRole(role);

        userRepository.save(user);
    }

    @Transactional
    @Override
    public void changeRole(long userId, Role role) {
        log.info("changing role for user with id: {}, now his role is: {}", userId, role);

        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("User with id: {} was not found", userId);
            return new UsernameNotFoundException("Username not found");
        });

        user.setRole(role);

        userRepository.save(user);
    }

    @Transactional
    @Override
    public void changePassword(ChangePasswordDto changePasswordDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> {
            log.error("User with email: {} was not found", email);
            return new UsernameNotFoundException("Username not found");
        });

        if (passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
            log.info("Password for user {} is changed", email);
            user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
            userRepository.save(user);
        } else {
            log.error("Attempt of password changing for user with email: {} failed - bad credentials", email);
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public long getUserIdByEmail(String email) {
        log.info("Getting user id by email: {}", email);
         return userRepository.findUserByEmail(email).orElseThrow(() -> {
            log.error("User with email: {} was not found", email);
            return new UsernameNotFoundException("Username not found");
        }).getId();
    }


    public Long getLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof SecurityUser)) {
            throw new AuthenticationRuntimeException();
        }
        return ((SecurityUser) authentication.getPrincipal()).getId();
    }

    @Override
    public UserRoleDto getIdentity() {
        User user = validateLoggedInUser();

        return userMapper.userToLoginDto(validateLoggedInUser());
    }

    @Override
    public User validateLoggedInUser() {
        Long loggedInUserId = getLoggedInUserId();
        return userRepository.findById(loggedInUserId).orElseThrow(() -> new AuthenticationRuntimeException());
    }

    @Override
    public boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }

        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals(role));
    }
}

