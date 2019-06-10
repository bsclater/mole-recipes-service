package com.example.recipes;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.database.Recipe;
import com.example.database.RecipeRepository;

@RestController
@RequestMapping("/api/recipes")
public class RecipeRestController {
 
    @Autowired
    private RecipeRepository RecipeRepository;

    @ApiOperation(value = "Returns all recipes.")
    @GetMapping
    public Iterable<Recipe> findAll() {
        return RecipeRepository.findAll();
    }

    @ApiOperation(value = "Get a recipe by specifying it's name.")
    @GetMapping("/name/{recipeName}")
    public List findByName(@PathVariable String recipeName) {
        return RecipeRepository.findByName(recipeName);
    }

    @ApiOperation(value = "Get a recipe by specifying it's ID.")
    @GetMapping("/{id}")
    public Recipe findOne(@PathVariable Long id) {
        return RecipeRepository.findById(id)
          .orElseThrow(RecipeNotFoundException::new);
    }

    @ApiOperation(value = "Create a recipe.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Recipe create(@RequestBody Recipe recipe) {
        return RecipeRepository.save(recipe);
    }

    @ApiOperation(value = "Delete a recipe.")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        RecipeRepository.findById(id)
          .orElseThrow(RecipeNotFoundException::new);
        RecipeRepository.deleteById(id);
    }

    @ApiOperation(value = "Update a recipe.")
    @PutMapping("/{id}")
    public Recipe updateRecipe(@RequestBody Recipe recipe, @PathVariable Long id) {
        if (recipe.getId() != id) {
          throw new RecipeIdMismatchException();
        }
        RecipeRepository.findById(id)
          .orElseThrow(RecipeNotFoundException::new);
        return RecipeRepository.save(recipe);
    }
}
