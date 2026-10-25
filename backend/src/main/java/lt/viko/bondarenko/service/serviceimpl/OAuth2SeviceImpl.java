package lt.viko.bondarenko.service.serviceimpl;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lt.viko.bondarenko.dto.response.JwtDto;
import lt.viko.bondarenko.entity.User;
import lt.viko.bondarenko.enums.AuthProvider;
import lt.viko.bondarenko.enums.Role;
import lt.viko.bondarenko.exception.OAuth2Exception;
import lt.viko.bondarenko.mappper.UserMapper;
import lt.viko.bondarenko.repository.UserRepository;
import lt.viko.bondarenko.service.JwtService;
import lt.viko.bondarenko.service.OAuth2Sevice;
import lt.viko.bondarenko.util.SecurityUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2SeviceImpl implements OAuth2Sevice {
    private static final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/tokeninfo?id_token=";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;

    @Override
    public JwtDto exchangeOAuthTokenForJWT(String oauthToken) {
        Map<String, Object> userInfo = validateGoogleToken(oauthToken);
        if (userInfo == null || !userInfo.containsKey("email"))
            throw new OAuth2Exception("Invalid OAuth token");
        UserDetails authenticatedUserDetails = findOrCreateUser(userInfo);
        return new JwtDto(jwtService.generateToken(authenticatedUserDetails));

    }

    private Map<String, Object> validateGoogleToken(String oauthToken) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            String url = GOOGLE_TOKEN_URL + oauthToken;
            return restTemplate.getForObject(url, Map.class);
        } catch (Exception e) {
            return null;
        }
    }

    private UserDetails findOrCreateUser(Map<String, Object> userInfo) {
        String email = (String) userInfo.get("email");
        String firstName = (String) userInfo.get("given_name");
        String lastName = (String) userInfo.get("family_name");

        Optional<User> existingUser = userRepository.findUserByEmail(email);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            /*
             * It is okey to allow users to login using both methods since our usual sign-up
             * process involves email verification
             * and such way is less confusig for users.
             *
             * OAuth using a local with its email is ok. Not the other way around.
             */
            return new SecurityUser(userMapper.userToLoginDto(user));
        }

        User newUser = User.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .role(Role.USER)
                .authProvider(AuthProvider.GOOGLE)
                .build();

        return new SecurityUser(userMapper.userToLoginDto(userRepository.save(newUser)));
    }

}