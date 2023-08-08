package com.gorest.Serenity.gorestInfo;

import com.gorest.Serenity.constants.EndPoints;
import com.gorest.Serenity.model.UserPojo;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class GoRestSteps {
    @Step("Create User")
    public Response createUser(String token, String name, String email, String gender, String status) {

        UserPojo userPojo = new UserPojo();
        userPojo.setName(name);
        userPojo.setEmail(email);
        userPojo.setGender(gender);
        userPojo.setStatus(status);

        return SerenityRest.given().log().all()
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + token)
                .when()
                .body(userPojo)
                .post(EndPoints.CREATE_USER);
    }
    @Step("Getting the User id with id : {0}")
    public int getUserId(int id){
        return SerenityRest.given()
                .header("Authorization","Bearer 13eb5d72be985bf927680ae4be97ec5c6f76dcffe0aaa2235c207d32925d495e")
                .pathParams("id",id)
                .when()
                .get(EndPoints.GET_SINGLE_USER_BY_ID)
                .then().statusCode(200)
                .extract()
                .path("id");
    }

    @Step("Update User")
    public ValidatableResponse updateUser(int userId, String token, String name, String updatedEmail, String gender, String status) {
        UserPojo userPojo = new UserPojo();
        userPojo.setName(name);
        userPojo.setEmail(updatedEmail);
        userPojo.setGender(gender);
        userPojo.setStatus(status);

        return SerenityRest.given().log().all()
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + token)
                .when()
                .body(userPojo)
                .patch(EndPoints.USERS + "/" + userId)
                .then().log().all();
    }

    @Step("USer Delete Successfully")
    public ValidatableResponse userDeleteSuccessfully(int userId, String token) {
        return SerenityRest.given().log().all()
                .headers("Content-Type", "application/json", "Authorization", "Bearer " + token)
                // .pathParam("id", userId)
                .when()
                .delete(EndPoints.USERS + "/" + userId)
                .then().log().all();

    }

    @Step("Getting all User information")
    public ValidatableResponse getAllUserInfo() {
        return SerenityRest.given()
                .header("Authorization", "Bearer 13eb5d72be985bf927680ae4be97ec5c6f76dcffe0aaa2235c207d32925d495e")
                .when()
                .get(EndPoints.USERS)
                .then();
    }


}
