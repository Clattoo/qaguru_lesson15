import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ReqresApiTests {

    @Test
    void successfulGetListOfUserTest() {
        get("https://reqres.in/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200);
    }

    @Test
    void amountOfKeysBodyDataListOfUserTest() {
        get("https://reqres.in/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .body("data", hasSize(6));
    }

    @Test
    void registerAccountWithRightCreditsTest() {
        String registerData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";

        given()
                .body(registerData)
                .contentType(JSON)
                .log().uri()

        .when()
                .post("https://reqres.in/api/register")

        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void registerAccountWithoutPasswordTest() {
        String registerData = "{\"email\": \"sydney@fife\"}";

        given()
                .body(registerData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void registerAccountWithWrongBodyTest() {
        String registerData = "#}";

        given()
                .body(registerData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400);
    }

}
