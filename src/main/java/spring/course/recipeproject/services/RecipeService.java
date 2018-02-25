package spring.course.recipeproject.services;

import spring.course.recipeproject.models.Recipe;

import java.util.Set;

/**
 * Created by cesljasdavor 25.02.18.
 */
public interface RecipeService {
    Set<Recipe> getRecipes();
}
