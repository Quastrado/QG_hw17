package models.user_update;

public record PartialUpdateUserBodyModel(
        String username,
        String email
) {
}