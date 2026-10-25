package lt.viko.bondarenko.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RestController;

import lt.viko.bondarenko.dto.response.JwtDto;
import lt.viko.bondarenko.service.OAuth2Sevice;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
public class OAuth2Controller {
    private final OAuth2Sevice service;

    @Operation(
            summary = "Google OAuth2 Token Login",
            description = "Exchange Google ID Token for application JWT.\n" +
                    "For testing: Get ID token from https://developers.google.com/oauthplayground/\n" +
                    "1. Select \"Google OAuth2 API v2\" scopes (email, profile)\n" +
                    "2. Authorize APIs\n" +
                    "3. Exchange authorization code for tokens\n" +
                    "4. Copy the \"id_token\" field (NOT access_token)\n" +
                    "5. Paste it as \"oauthToken\" in request body"
    )
    @PostMapping("/login/oauth2/code/google")
    public ResponseEntity<JwtDto> exchangeOAuthToken(@RequestBody Map<String, String> body) {
        String oauthToken = body.get("oauthToken");

        return new ResponseEntity<>(service.exchangeOAuthTokenForJWT(oauthToken), HttpStatus.OK);
    }

}
