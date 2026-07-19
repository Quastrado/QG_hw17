package models.user_update;

public record UpdateUserBodyModel(
        String username,
        String firstName,
        String lastName,
        String email
) {
}