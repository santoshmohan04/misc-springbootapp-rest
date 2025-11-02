package com.example.demo.service;

import com.example.demo.model.Recipe;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Get all recipes (optionally only those created by the logged-in user)
     */
    public List<Recipe> getAllRecipes(String token) {
        String email = jwtUtil.extractUsername(token);
        return recipeRepository.findByCreatedBy(email);
    }

    /**
     * Get a recipe by its ID
     */
    public Optional<Recipe> getRecipeById(String id) {
        return recipeRepository.findById(id);
    }

    /**
     * Add a new recipe for the logged-in user
     */
    public Recipe addRecipe(Recipe recipe, String token) {
        String email = jwtUtil.extractUsername(token);
        recipe.setCreatedBy(email);
        return recipeRepository.save(recipe);
    }

    /**
     * Update an existing recipe — only if it belongs to the logged-in user
     */
    public Recipe updateRecipe(String id, Recipe updatedRecipe, String token) {
        String email = jwtUtil.extractUsername(token);
        Optional<Recipe> existingRecipeOpt = recipeRepository.findById(id);

        if (existingRecipeOpt.isPresent()) {
            Recipe existingRecipe = existingRecipeOpt.get();
            if (!existingRecipe.getCreatedBy().equals(email)) {
                throw new RuntimeException("You are not authorized to update this recipe");
            }

            existingRecipe.setName(updatedRecipe.getName());
            existingRecipe.setDescription(updatedRecipe.getDescription());
            existingRecipe.setImagePath(updatedRecipe.getImagePath());
            existingRecipe.setIngredients(updatedRecipe.getIngredients());

            return recipeRepository.save(existingRecipe);
        } else {
            throw new RuntimeException("Recipe not found with id: " + id);
        }
    }

    /**
     * Delete a recipe — only if it belongs to the logged-in user
     */
    public void deleteRecipe(String id, String token) {
        String email = jwtUtil.extractUsername(token);
        Optional<Recipe> existingRecipeOpt = recipeRepository.findById(id);

        if (existingRecipeOpt.isPresent()) {
            Recipe existingRecipe = existingRecipeOpt.get();
            if (!existingRecipe.getCreatedBy().equals(email)) {
                throw new RuntimeException("You are not authorized to delete this recipe");
            }
            recipeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Recipe not found with id: " + id);
        }
    }
}