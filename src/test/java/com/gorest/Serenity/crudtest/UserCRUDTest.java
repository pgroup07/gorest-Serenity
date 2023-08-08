package com.gorest.Serenity.crudtest;

import com.gorest.Serenity.gorestInfo.GoRestSteps;
import com.gorest.Serenity.testbase.TestBase;
import com.gorest.Serenity.utils.TestUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class UserCRUDTest extends TestBase {
    static String name = "Prime" + TestUtils.getRandomValue();
    static String email = TestUtils.getRandomValue() + "Prime@gmail.com";
    static String updatedEmail = "Updated" + TestUtils.getRandomValue() + "@gmail.com";
    static String gender = "male";
    static String status = "active";
    static int userId;
    static String token = "d88d02069e48aedd8e5032e17f21b929e78721f871e7289b0dc925c3571ee4f7";

    public static Response response;
    public static ValidatableResponse validatableResponse;

    @Steps
    GoRestSteps goRestSteps;

    @Title("This wii create a new User")
    @Test()
    public void aVerifyUserCreatedSuccessfully() {

        response = goRestSteps.createUser(token, name, email, gender, status);
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(responseBody);
        userId = jsonPath.getInt("id");
    }

    @Title("Update the user information and Verify if the user is added to application")
    @Test
    public void bVerifyUserUpdateSuccessfully() {

        validatableResponse = goRestSteps.updateUser(userId, token, name, updatedEmail, gender, status).statusCode(200);
        String exceptedMessage = updatedEmail;
        String actualMessage = validatableResponse.extract().path("email");

        Assert.assertEquals(exceptedMessage, actualMessage);

    }

    @Title("This will delete student and verify that student is deleted")
    @Test
    public void zVerifyUserDeleteSuccessfully() {
        goRestSteps.userDeleteSuccessfully(userId, token).statusCode(204);
    }

}