package lt.viko.bondarenko.controller;

import lt.viko.bondarenko.dto.user.UserRoleDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.viko.bondarenko.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/identity")
@RequiredArgsConstructor
public class IdentityControler {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserRoleDto> getMethodName() {
        return ResponseEntity.ok(userService.getIdentity());
    }

}
