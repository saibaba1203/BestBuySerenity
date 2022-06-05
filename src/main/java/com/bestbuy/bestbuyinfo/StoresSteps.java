package com.bestbuy.bestbuyinfo;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.StoresPojo;
import java.util.HashMap;

public class StoresSteps {
    @Step("Creating store with name: {0}, type: {1}, address: {2}, address2: {3}, city: {4}, " +
            "state: {5}, zip: {6}, lat: {7}, lng: {8}, hours: {9}")
    public ValidatableResponse createStore (String name, String type, String address, String address2, String city,
                                            String state, String zip, double lat, double lng, String hours){
        StoresPojo storesPojo = StoresPojo.getStorePojo(name, type, address, address2, city, state, zip, lat, lng,hours);
        return SerenityRest.given().log().all()
                .contentType((ContentType.JSON))
                .body(storesPojo)
                .when()
                .post()
                .then();
    }

    @Step ("Fetching the store information with name: {0}")
    public HashMap<String, Object> getStoreInfoByname(String name){
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

    @Step ("Updating store with name: {0}, type: {1}, address: {2}, address2: {3}, city: {4}, " +
            "state: {5}, zip: {6}, lat: {7}, lng: {8}, hours: {9}")
    public ValidatableResponse updateStore(int storeID, String name, String type, String address, String address2, String city,
                                           String state, String zip, double lat, double lng, String hours){
        StoresPojo storesPojo = StoresPojo.getStorePojo(name, type, address, address2, city, state, zip, lat, lng,hours);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("storeID", storeID)
                .body(storesPojo)
                .when()
                .put(EndPoints.UPDATE_STORE_BY_ID)
                .then();

    }

    @Step ("Deleting store information with storeID: {0}")
    public ValidatableResponse deleteStore(int storeID){
        return SerenityRest
                .given()
                .pathParam("storeID", storeID)
                .when()
                .delete(EndPoints.DELETE_STORE_BY_ID)
                .then();
    }

    @Step("Fetching store information with storeID: {0}")
    public ValidatableResponse getStoreById(int storeID){
        return SerenityRest
                .given()
                .pathParam("storeID", storeID)
                .when()
                .delete(EndPoints.GET_SINGLE_STORE_BY_ID)
                .then();
    }
}
