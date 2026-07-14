package tests;

import models.registration.RegistrationBodyModel;
import models.registration.SuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.Test;
import tests.testdata.TestDataBookClub;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static specs.registration.RegistrationSpec.registrationRequestSpec;
import static specs.registration.RegistrationSpec.successfulRegistrationResponseSpec;

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
                .post("/user/register/")
                .then()
                .spec(successfulRegistrationResponseSpec)
                .extract()
                .as(SuccessfulRegistrationResponseModel.class);

        String userName = successfulRegistrationResponseModel.username();

        assertThat(userName).isEqualTo(testData.username);
    }
}
