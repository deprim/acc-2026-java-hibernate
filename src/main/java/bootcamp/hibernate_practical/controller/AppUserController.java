package bootcamp.hibernate_practical.controller;

import bootcamp.hibernate_practical.dto.CreateUserRequest;
import bootcamp.hibernate_practical.service.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody CreateUserRequest createUserRequest) {
        appUserService.registerUser(createUserRequest.username(), createUserRequest.password());
        return ResponseEntity.ok().body(Map.of("message", "User registered successfully"));
    }

}
