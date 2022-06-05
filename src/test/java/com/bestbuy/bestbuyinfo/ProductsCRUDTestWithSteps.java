package com.bestbuy.bestbuyinfo;

import com.bestbuy.testbase.TestBaseForProducts;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ProductsCRUDTestWithSteps extends TestBaseForProducts {
    static String name = "Apple HomePod Mini ";
    static String type = "HardGood";
    static int price = 99;
    static int shipping = 10;
    static String upc = "123433429874";
    static String description = "Apple HomePod Mini Smart Speaker with Siri";
    static String manufacturer = "Apple";
    static String model = "HomePod Mini";
    static String url = TestUtils.getRandomName();
    static String image = TestUtils.getRandomName();
    static int productId;

    @Steps
    ProductsSteps productsSteps;

    @Title("This will create new product")
    @Test
    public void test001(){
        ValidatableResponse response = productsSteps.createProduct(name, type, price, shipping,
                upc, description, manufacturer, model, url, image);
        response.log().all().statusCode(201);
    }

    @Title ("Verify if the product was added to the application")
    @Test
    public void test002(){
        HashMap<String, Object> value = productsSteps.getProductInfoByname(name);
        Assert.assertThat(value, hasValue(name));
        productId = (int) value.get("id");
    }

    @Title ("Update product information and verify the updated information")
    @Test
    public void test003(){
        name = name + " (Updated)";
        description = description + " (Updated)";
        productsSteps.updateProduct(productId,name, type, price, shipping,
                upc, description, manufacturer, model, url, image).log().all().statusCode(200);
        HashMap<String, Object> value = productsSteps.getProductInfoByname(name);
        Assert.assertThat(value, hasValue(name));
    }

    @Title ("Delete product and verify if the product is deleted")
    @Test
    public void test004(){
        productsSteps.deleteProduct(productId).statusCode(200);
        productsSteps.getProductById(productId).statusCode(404);
    }
}
