package com.bestbuy.bestbuyinfo;

import com.bestbuy.testbase.TestBaseForStores;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

public class StoresCRUDTestWithSteps extends TestBaseForStores {
    static String name = "Crawley";
    static String type = "BigBox";
    static String address = "100 Downland Drive";
    static String address2 = "London Road";
    static String city = "Crawley";
    static String state = "Sussex";
    static String zip = "55305";
    static double lat = 44.969696;
    static double lng = -93.445679;
    static String hours= "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";
    static int storeId;

    @Steps
    StoresSteps storesSteps;

    @Title("This will create new store")
    @Test
    public void test001(){
        ValidatableResponse response = storesSteps.createStore(name, type, address, address2, city, state, zip, lat, lng,hours);
        response.log().all().statusCode(201);
    }

    @Title("Verify if the strore was created in application")
    @Test
    public void test002(){
        HashMap<String, Object> value = storesSteps.getStoreInfoByname(name);
        Assert.assertThat(value, hasValue(name));
        storeId = (int) value.get("id");
    }

    @Title ("Update store information and verify the updated information")
    @Test
    public void test003(){
        name = name + " (Updated)";
        address = address + " (Updated)";
        storesSteps.updateStore(storeId, name, type, address, address2, city, state, zip, lat, lng,hours).log().all().statusCode(200);
        HashMap<String, Object> value = storesSteps.getStoreInfoByname(name);
        Assert.assertThat(value, hasValue(name));
    }

    @Title("Delete store and verify if the product is deleted")
    @Test
    public void test004(){
        storesSteps.deleteStore(storeId).statusCode(200);
        storesSteps.getStoreById(storeId).statusCode(404);
    }
}
