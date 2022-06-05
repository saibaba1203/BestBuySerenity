package com.bestbuy.bestbuyinfo;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ServicesPojo;
import java.util.HashMap;

public class ServicesSteps {
    @Step("Creating services with name: {0}")
    public ValidatableResponse createService(String name){
        ServicesPojo servicesPojo = ServicesPojo.getServicesPojo(name);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(servicesPojo)
                .when()
                .post()
                .then();
    }

    @Step ("Fetching the services information with name: {0}")
    public HashMap<String, Object> getServicesInfoByName(String name){
        String p1 = "data.findAll{it.name='";
        String p2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + name + p2);
    }

    @Step("Updating Service information with name : {0}")
    public ValidatableResponse updateService(String name, int serviceId) {
        ServicesPojo servicesPojo = ServicesPojo.getServicesPojo(name);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("serviceID", serviceId)
                .body(servicesPojo)
                .when()
                .put(EndPoints.UPDATE_SERVICE_BY_ID)
                .then();
    }

    @Step("Deleting Service information with ServiceID: {0}")
    public ValidatableResponse deleteService(int serviceId) {
        return SerenityRest
                .given()
                .pathParam("serviceID", serviceId)
                .when()
                .delete(EndPoints.DELETE_SERVICE_BY_ID)
                .then();
    }

    @Step("Fetching Service information with ServiceID: {0}")
    public ValidatableResponse getServiceById(int serviceId) {
        return SerenityRest
                .given()
                .pathParam("serviceID", serviceId)
                .when()
                .get(EndPoints.GET_SINGLE_SERVICE_BY_ID)
                .then();
    }
}
