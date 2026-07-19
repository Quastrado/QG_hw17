package models.user_update;

import java.util.List;

public record IncorrectPartialUpdateUserResponseModel(
        List<String> username,
        List<String> firstName,
        List<String>lastName,
        List<String>email,
        List<String> remoteAddr
) {
}
