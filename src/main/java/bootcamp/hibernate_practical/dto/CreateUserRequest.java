package bootcamp.hibernate_practical.dto;

public record CreateUserRequest(
        // no validation, it just for testing purposes that registration works
        String username,
        String password
) {
}
