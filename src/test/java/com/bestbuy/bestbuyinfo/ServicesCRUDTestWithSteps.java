package com.bestbuy.bestbuyinfo;

import com.bestbuy.testbase.TestBaseForServices;
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
public class ServicesCRUDTestWithSteps extends TestBaseForServices {
    static String name = "Apple Shop";
    static Integer servicesId;

    @Steps
    ServicesSteps servicesSteps;

    @Title("This will create new store")
    @Test
    public void test001(){
        ValidatableResponse response = servicesSteps.createService(name);
        response.log().all().statusCode(201);
    }

    @Title("Verify if the services is created")
    @Test
    public void test002(){
        HashMap<String, Object> value = servicesSteps.getServicesInfoByName(name);
        Assert.assertThat(value, hasValue(name));
        servicesId = (int) value.get("id");
    }
    @Title("Update Service information and verify the updated information")
    @Test
    public void test003() {
        name = name + " (Updated)";
        servicesSteps.updateService(name, servicesId).log().all().statusCode(200);
        HashMap<String, Object> value = servicesSteps.getServicesInfoByName(name);
        Assert.assertThat(value, hasValue(name));
    }

    @Title("Delete Service and verify if the Service is deleted")
    @Test
    public void test004() {
        servicesSteps.deleteService(servicesId).statusCode(200);
        servicesSteps.getServiceById(servicesId).statusCode(404);
    }
}
