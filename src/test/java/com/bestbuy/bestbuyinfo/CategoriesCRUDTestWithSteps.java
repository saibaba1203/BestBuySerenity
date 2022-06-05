package com.bestbuy.bestbuyinfo;

import com.bestbuy.testbase.TestBaseForCategories;
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
public class CategoriesCRUDTestWithSteps extends TestBaseForCategories {
    static String name = "Electronics & Computers" + TestUtils.getRandomValue();
    static String id = TestUtils.getRandomName();
    static String categoryId;

    @Steps
    CategoriesSteps categoriesSteps;


    @Title("This will create new Category")
    @Test
    public void test001() {
        ValidatableResponse response = categoriesSteps.createCategory(name, id);
        response.log().all().statusCode(201);
    }

    @Title("Verify if the Category was created in application")
    @Test
    public void test002() {
        HashMap<String, Object> value = categoriesSteps.getCategoryInfoByName(name);
        Assert.assertThat(value, hasValue(name));
        categoryId = (String) value.get("id");
    }

    @Title("Update Category information and verify the updated information")
    @Test
    public void test003() {
        name = name + " (Updated)";
        categoriesSteps.updateCategory(name, categoryId).log().all().statusCode(200);
        HashMap<String, Object> value = categoriesSteps.getCategoryInfoByName(name);
        Assert.assertThat(value, hasValue(name));
    }

    @Title("Delete Category and verify if the Category is deleted")
    @Test
    public void test004() {
        categoriesSteps.deleteCategory(categoryId).statusCode(200);
        categoriesSteps.getCategoryById(categoryId).statusCode(404);
    }

}
