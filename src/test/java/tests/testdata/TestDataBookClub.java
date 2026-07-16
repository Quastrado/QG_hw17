package tests.testdata;

import net.datafaker.Faker;

public class TestDataBookClub {

    Faker faker = new Faker();

    public String username = faker.name().firstName();
    public String firstName = faker.name().firstName();
    public String lastName = faker.name().lastName();
    public String email = faker.internet().emailAddress();
    public String password = faker.regexify("[A-Za-z0-9]{9}");
    public String wrongPassword = password + " ";
    public String existingUserRegistrationError = "A user with that username already exists.";
    public String expectedTokenPart = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
    public String wrongCredentialsError = "Invalid username or password.";
    public String requiredFieldError = "This field is required.";
    public String wrongTokenTypeError = "Token has wrong type";
    public String notValidTokenCodeError = "token_not_valid";
}
