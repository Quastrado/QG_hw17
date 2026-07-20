package tests;

import models.login.*;
import models.logout.WrongRefreshTokenResponseModel;
import org.junit.jupiter.api.Test;
import tests.testdata.TestDataBookClub;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.BaseSpec.baseRequestSpec;
import static specs.registration.RegistrationSpec.successfulRegistrationResponseSpec;
import static specs.login.LoginSpec.*;

public class LoginTest extends TestBase {
    TestDataBookClub testData = new TestDataBookClub();

    @Test
    public void successfulLogin() {

        LoginBodyModel registrationData = new LoginBodyModel(
                testData.username,
                testData.password
        );

        given(baseRequestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(successfulRegistrationResponseSpec);

        LoginBodyModel loginData = new LoginBodyModel(
                testData.username,
                testData.password
        );

        SuccessfulLoginResponseModel loginResponse = given(baseRequestSpec)
                .body(loginData)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract().as(SuccessfulLoginResponseModel.class);

        String access = loginResponse.access();
        String refresh = loginResponse.refresh();

        assertThat(access).startsWith(testData.expectedTokenPart);
        assertThat(refresh).startsWith(testData.expectedTokenPart);
        assertThat(access).isNotEqualTo(refresh);
    }

    @Test
    public void wrongCredentialsLoginTest() {

        LoginBodyModel registrationData = new LoginBodyModel(
                testData.username,
                testData.wrongPassword
        );

        WrongCredentialsLoginResponseModel loginResponse = given(baseRequestSpec)
                .body(registrationData)
                .when()
                .post("/auth/token/")
                .then()
                .spec(wrongCredentialsLoginResponseSpec)
                .extract().as(WrongCredentialsLoginResponseModel.class);

        String error = loginResponse.detail();
        assertThat(error).isEqualTo(testData.wrongCredentialsError);
    }

    @Test
    public void emptyRefreshTokenLoginTest() {
        WithoutRefreshTokenLoginBodyModel emptyRefreshToken = new WithoutRefreshTokenLoginBodyModel();
        WithoutRefreshTokenLoginResponseModel emptyRefreshResponseModel = given(baseRequestSpec)
                .body(emptyRefreshToken)
                .when()
                .post("/auth/token/refresh/")
                .then()
                .spec(withoutRefreshTokenResponseSpec)
                .extract().as(WithoutRefreshTokenLoginResponseModel.class);

        String actualRefresh = emptyRefreshResponseModel.refresh().get(0);
        assertThat(actualRefresh).isEqualTo(testData.requiredFieldError);
    }

    @Test
    public void wrongRefreshTokenLoginTest() {
        InvalidRefreshTokenBodyModel invalidTokenBodyModel = new InvalidRefreshTokenBodyModel(
                testData.wrongRefreshToken
        );
        WrongRefreshTokenResponseModel loginResponse = given(baseRequestSpec)
                .body(invalidTokenBodyModel)
                .when()
                .post("/auth/token/refresh/")
                .then()
                .spec(wrongRefreshTokenResponseSpec)
                .extract().as(WrongRefreshTokenResponseModel.class);

        String detailInvalidRefreshToken = loginResponse.detail();
        String codeInvalidRefreshToken = loginResponse.code();

        assertThat(detailInvalidRefreshToken).isEqualTo(testData.invalidTokenError);
        assertThat(codeInvalidRefreshToken).isEqualTo(testData.notValidTokenCodeError);
    }
}
