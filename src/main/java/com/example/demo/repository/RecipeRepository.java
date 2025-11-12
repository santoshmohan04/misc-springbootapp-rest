package com.example.demo.repository;

import com.example.demo.model.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String> {

    // Find all recipes created by a specific user
    List<Recipe> findByCreatedBy(String createdBy);

    // Search recipes by name (case-insensitive)
    List<Recipe> findByNameContainingIgnoreCase(String name);

    // Search recipes by ingredient (case-insensitive)
    List<Recipe> findByIngredientsContainingIgnoreCase(String ingredient);
}