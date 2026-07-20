package specs.logout;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.notNullValue;


public class LogoutSpec {


    public static ResponseSpecification successfulLogoutResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/logout/successful_logout_response_schema.json"
            ))
            .build();

    public static ResponseSpecification invalidTokenLogoutResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(401)
            .expectBody("detail", notNullValue())
            .expectBody("code", notNullValue())
            .build();

    public static ResponseSpecification withoutRefreshTokenLogoutResponseSpec = new ResponseSpecBuilder()
            .log(ALL)
            .expectStatusCode(400)
            .expectBody("refresh", notNullValue())
            .build();

}
