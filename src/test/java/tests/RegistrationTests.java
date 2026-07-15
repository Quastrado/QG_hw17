package tests;

import models.registration.ExistingUserResponseModel;
import models.registration.RegistrationBodyModel;
import models.registration.SuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.Test;
import tests.testdata.TestDataBookClub;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static specs.registration.RegistrationSpec.*;

public class RegistrationTests extends TestBase {
    TestDataBookClub testData = new TestDataBookClub();

    @Test
    public void successfulRegistrationTest() {

        RegistrationBodyModel registrationBodyModel = new RegistrationBodyModel(
                testData.username,
                testData.password
        );
        SuccessfulRegistrationResponseModel successfulRegistrationResponseModel = given(registrationRequestSpec)
                .body(registrationBodyModel)
                .when()
                .post("/users/register/")
                .then()
                .spec(successfulRegistrationResponseSpec)
                .extract()
                .as(SuccessfulRegistrationResponseModel.class);

        String userName = successfulRegistrationResponseModel.username();

        assertThat(userName).isEqualTo(testData.username);
    }

    @Test
    public void existingUserRegistrationTest() {

        RegistrationBodyModel registrationBodyModel = new RegistrationBodyModel(
                testData.username,
                testData.password
        );
        SuccessfulRegistrationResponseModel firstRegistrationResponse = given(registrationRequestSpec)
                .body(registrationBodyModel)
                .when()
                .post("/users/register/")
                .then()
                .spec(successfulRegistrationResponseSpec)
                .extract()
                .as(SuccessfulRegistrationResponseModel.class);

        ExistingUserResponseModel secondRegistrationResponse = given(registrationRequestSpec)
                .body(registrationBodyModel)
                .when()
                .post("/users/register/")
                .then()
                .spec(existingUserRegistrationResponseSpec)
                .extract()
                .as(ExistingUserResponseModel.class);

        String error = secondRegistrationResponse.username().get(0);
        assertThat(error).isEqualTo(testData.existingUserRegistrationError);
    }
}
