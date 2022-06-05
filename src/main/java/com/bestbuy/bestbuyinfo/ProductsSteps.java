package com.bestbuy.bestbuyinfo;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductsPojo;
import java.util.HashMap;

public class ProductsSteps {
    @Step("Creating product with name: {0}, type {1}, price {2}, shipping {3}, upc {4}, description {5}, " +
            "manufacturer {6}, model {7}, url {8}, image {9}")
    public ValidatableResponse createProduct (String name, String type, int price, int shipping, String upc,
                                              String description, String manufacturer,
                                              String model, String url, String image){
        ProductsPojo productsPojo = ProductsPojo.getProductPojo(name, type, price, shipping,
                upc, description, manufacturer, model, url, image);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(productsPojo)
                .when()
                .post()
                .then();
    }

    @Step ("Fetching the product information with name: {0}")
    public HashMap<String, Object> getProductInfoByname(String name){
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

    @Step ("Updating product with name: {0}, type {1}, price {2}, shipping {3}, upc {4}, description {5}, manufacturer {6}, model {7}, url {8}, image {9}")
    public ValidatableResponse updateProduct(int productID, String name, String type, int price, int shipping, String upc,
                                             String description, String manufacturer,
                                             String model, String url, String image){
        ProductsPojo productsPojo = ProductsPojo.getProductPojo(name, type, price, shipping,
                upc, description, manufacturer, model, url, image);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("productID", productID)
                .body(productsPojo)
                .when()
                .put(EndPoints.UPDATE_PRODUCT_BY_ID)
                .then();
    }

    @Step ("Deleting product information with productID: {0}")
    public ValidatableResponse deleteProduct(int productID){
        return SerenityRest
                .given()
                .pathParam("productID", productID)
                .when()
                .delete(EndPoints.DELETE_PRODUCT_BY_ID)
                .then();
    }

    @Step ("Fetching product information with productID: {0}")
    public ValidatableResponse getProductById(int productID){
        return SerenityRest
                .given()
                .pathParam("productID", productID)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then();
    }
}
