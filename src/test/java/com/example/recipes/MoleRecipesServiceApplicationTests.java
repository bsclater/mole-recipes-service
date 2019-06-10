package com.example.recipes;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.database.Recipe;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MoleRecipesServiceApplicationTests {

    private static final String API_ROOT = "http://localhost:8081/api/recipes";

    @Test
    public void whenGetAllRecipes_thenOK() {
        final Response response = RestAssured.get(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetRecipesByTitle_thenOK() {
        final Recipe recipe = createRandomRecipe();
        createRecipeAsUri(recipe);

        final Response response = RestAssured.get(API_ROOT + "/name/" + recipe.getName());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class)
            .size() > 0);
    }

    @Test
    public void whenGetCreatedRecipeById_thenOK() {
        final Recipe recipe = createRandomRecipe();
        final String location = createRecipeAsUri(recipe);

        final Response response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(recipe.getName(), response.jsonPath()
            .get("name"));
    }

    @Test
    public void whenGetNotExistRecipeById_thenNotFound() {
        final Response response = RestAssured.get(API_ROOT + "/" + randomNumeric(4));
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    // POST
    @Test
    public void whenCreateNewRecipe_thenCreated() {
        final Recipe recipe = createRandomRecipe();

        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(recipe)
            .post(API_ROOT);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenInvalidRecipe_thenError() {
        final Recipe recipe = createRandomRecipe();
        recipe.setMethod(null);

        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(recipe)
            .post(API_ROOT);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedRecipe_thenUpdated() {
        final Recipe recipe = createRandomRecipe();
        final String location = createRecipeAsUri(recipe);

        recipe.setId(Long.parseLong(location.split("api/recipes/")[1]));
        recipe.setMethod("Mix it good");
        Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(recipe)
            .put(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("Mix it good", response.jsonPath()
            .get("method"));

    }

    @Test
    public void whenDeleteCreatedRecipe_thenOk() {
        final Recipe recipe = createRandomRecipe();
        final String location = createRecipeAsUri(recipe);

        Response response = RestAssured.delete(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    // ===============================

    private Recipe createRandomRecipe() {
        final Recipe recipe = new Recipe();
        recipe.setName(randomAlphabetic(10));
        recipe.setMethod(randomAlphabetic(15));
        return recipe;
    }

    private String createRecipeAsUri(Recipe recipe) {
        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(recipe)
            .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath()
            .get("id");
    }


}
