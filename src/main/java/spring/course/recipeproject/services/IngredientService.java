package spring.course.recipeproject.services;

import spring.course.recipeproject.commands.IngredientCommand;

/**
 * Created by cesljasdavor 02.03.18.
 */
public interface IngredientService {
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteByRecipeIdAndIngredientId(Long recipeId, Long id);
}
