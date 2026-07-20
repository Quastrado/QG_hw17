package tests;

import models.login.LoginBodyModel;
import models.login.SuccessfulLoginResponseModel;
import models.registration.RegistrationBodyModel;
import models.user_update.SuccessfulUpdateUserResponseModel;
import models.user_update.*;
import org.junit.jupiter.api.Test;
import tests.testdata.TestDataBookClub;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static specs.BaseSpec.baseRequestSpec;
import static specs.login.LoginSpec.successfulLoginResponseSpec;
import static specs.registration.RegistrationSpec.successfulRegistrationResponseSpec;
import static specs.user_update.UpdateUserSpec.*;

public class UpdateUserTest extends TestBase{
    TestDataBookClub testData = new TestDataBookClub();

    @Test
    public void successfulUpdateUserWithPutTest() {

        RegistrationBodyModel registrationData = new RegistrationBodyModel(testData.username, testData.password);
        given(baseRequestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(successfulRegistrationResponseSpec);

        LoginBodyModel dataLogin = new LoginBodyModel(testData.username, testData.password);
        SuccessfulLoginResponseModel responseLogin = given(baseRequestSpec)
                .body(dataLogin)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract()
                .as(SuccessfulLoginResponseModel.class);

        String accessToken = responseLogin.access();

        UpdateUserBodyModel dataUpdateUser = new UpdateUserBodyModel(
                testData.username,
                testData.firstName,
                testData.lastName,
                testData.email
        );
        SuccessfulUpdateUserResponseModel responseUpdateUser =
                given(baseRequestSpec)
                        .header("Authorization", "Bearer " + accessToken)
                        .body(dataUpdateUser)
                        .when()
                        .put("/users/me/")
                        .then()
                        .spec(successfulUpdateUserResponseSpec)
                        .extract()
                        .as(SuccessfulUpdateUserResponseModel.class);

        String actualUsername = responseUpdateUser.username();
        String actualFirstName = responseUpdateUser.firstName();
        String actualLastName = responseUpdateUser.lastName();
        String actualEmail = responseUpdateUser.email();

        assertThat(responseUpdateUser.id()).isPositive();
        assertThat(actualUsername).isEqualTo(testData.username);
        assertThat(actualFirstName).isEqualTo(testData.firstName);
        assertThat(actualLastName).isEqualTo(testData.lastName);
        assertThat(actualEmail).isEqualTo(testData.email);
        assertThat(responseUpdateUser.remoteAddr()).isNotBlank();
    }

    @Test
    public void successfulUpdateUserWithPatchTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel(testData.username, testData.password);
        given(baseRequestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(successfulRegistrationResponseSpec);

        LoginBodyModel dataLogin = new LoginBodyModel(testData.username, testData.password);
        SuccessfulLoginResponseModel responseLogin = given(baseRequestSpec)
                .body(dataLogin)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract().as(SuccessfulLoginResponseModel.class);

        String accessToken = responseLogin.access();

        UpdateUserBodyModel dataUpdateUser = new UpdateUserBodyModel(
                testData.username,
                testData.firstName,
                testData.lastName,
                testData.email);
        SuccessfulUpdateUserResponseModel responseUpdateUser =
                given(baseRequestSpec)
                        .header("Authorization", "Bearer " + accessToken)
                        .body(dataUpdateUser)
                        .when()
                        .patch("/users/me/")
                        .then()
                        .spec(successfulUpdateUserResponseSpec)
                        .extract()
                        .as(SuccessfulUpdateUserResponseModel.class);

        String actualUsername = responseUpdateUser.username();
        String actualFirstName = responseUpdateUser.firstName();
        String actualLastName = responseUpdateUser.lastName();
        String actualEmail = responseUpdateUser.email();

        assertThat(responseUpdateUser.id()).isPositive();
        assertThat(actualUsername).isEqualTo(testData.username);
        assertThat(actualFirstName).isEqualTo(testData.firstName);
        assertThat(actualLastName).isEqualTo(testData.lastName);
        assertThat(actualEmail).isEqualTo(testData.email);
        assertThat(responseUpdateUser.remoteAddr()).isNotBlank();
    }

    @Test
    public void successfulPartialUpdateUserWithPatchTest() {
        RegistrationBodyModel registrationData = new RegistrationBodyModel(testData.username, testData.password);
        given(baseRequestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(successfulRegistrationResponseSpec);

        LoginBodyModel dataLogin = new LoginBodyModel(testData.username, testData.password);
        SuccessfulLoginResponseModel responseLogin = given(baseRequestSpec)
                .body(dataLogin)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract()
                .as(SuccessfulLoginResponseModel.class);

        String accessToken = responseLogin.access();

        PartialUpdateUserBodyModel dataUpdateUser =
                new PartialUpdateUserBodyModel(testData.username, testData.email);
        SuccessfulUpdateUserResponseModel responseUpdateUser =
                given(baseRequestSpec)
                        .header("Authorization", "Bearer " + accessToken)
                        .body(dataUpdateUser)
                        .when()
                        .patch("/users/me/")
                        .then()
                        .spec(successfulUpdateUserResponseSpec)
                        .extract()
                        .as(SuccessfulUpdateUserResponseModel.class);

        String actualUsername = responseUpdateUser.username();
        String actualEmail = responseUpdateUser.email();

        assertThat(responseUpdateUser.id()).isPositive();
        assertThat(actualUsername).isEqualTo(testData.username);
        assertThat(actualEmail).isEqualTo(testData.email);
        assertThat(responseUpdateUser.remoteAddr()).isNotBlank();
    }

    @Test
    public void partialUpdateUserWithPutNegativeTest() {

        RegistrationBodyModel registrationData = new RegistrationBodyModel(testData.username, testData.password);
        given(baseRequestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(successfulRegistrationResponseSpec);

        LoginBodyModel dataLogin = new LoginBodyModel(testData.username, testData.password);
        SuccessfulLoginResponseModel responseLogin = given(baseRequestSpec)
                .body(dataLogin)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract()
                .as(SuccessfulLoginResponseModel.class);

        String accessToken = responseLogin.access();

        PartialUpdateUserBodyModel dataUpdateUser =
                new PartialUpdateUserBodyModel(testData.username, testData.email);
        IncorrectPartialUpdateUserResponseModel responseUpdateUser =
                given(baseRequestSpec)
                        .header("Authorization", "Bearer " + accessToken)
                        .body(dataUpdateUser)
                        .when()
                        .put("/users/me/")
                        .then()
                        .spec(unsuccessfulPartialUpdateUserResponseSpec)
                        .extract()
                        .as(IncorrectPartialUpdateUserResponseModel.class);

        String actualFirstName = responseUpdateUser.firstName().get(0);
        String actualLastName = responseUpdateUser.lastName().get(0);

        assertThat(actualFirstName).isEqualTo(testData.requiredFieldError);
        assertThat(actualLastName).isEqualTo(testData.requiredFieldError);
        assertThat(responseUpdateUser.username()).isNull();
        assertThat(responseUpdateUser.email()).isNull();
    }

    @Test
    public void withoutRequiredAuthorizationHeaderUpdateUserNegativeTest() {

        UpdateUserBodyModel dataUpdateUser = new UpdateUserBodyModel(
                testData.username,
                testData.firstName,
                testData.lastName,
                testData.email
        );
        UnauthorizedResponseModel responseUpdateUser =
                given(baseRequestSpec)
                        .body(dataUpdateUser)
                        .when()
                        .put("/users/me/")
                        .then()
                        .spec(unauthorizedResponseSpec)
                        .extract()
                        .as(UnauthorizedResponseModel.class);

        String actualDetail = responseUpdateUser.detail();
        assertThat(actualDetail).isEqualTo(testData.unauthorizedError);
    }

}
