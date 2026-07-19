package models.user_update;

public record SuccessfulUpdateUserResponseModel(
        int id,
        String username,
        String firstName,
        String lastName,
        String email,
        String remoteAddr
) {
}