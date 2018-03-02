package spring.course.recipeproject.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.course.recipeproject.commands.IngredientCommand;
import spring.course.recipeproject.converters.IngredientCommandToIngredient;
import spring.course.recipeproject.converters.IngredientToIngredientCommand;
import spring.course.recipeproject.models.Ingredient;
import spring.course.recipeproject.models.Recipe;
import spring.course.recipeproject.repositories.RecipeRepository;
import spring.course.recipeproject.repositories.UnitOfMeasureRepository;

import java.util.Optional;
import java.util.Set;

/**
 * Created by cesljasdavor 02.03.18.
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private RecipeRepository recipeRepository;
    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private IngredientCommandToIngredient ingredientCommandToIngredient;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id) {
        return recipeRepository.findById(recipeId)
                                .map(recipe -> findById(id, recipe))
                                .orElseGet(() -> {
                                    log.debug(String.format("No ingredient with id %d or no recipe with id %d ", id, recipeId));
                                    return null;
                                });
    }

    private IngredientCommand findById(Long id, Recipe recipe) {
        Set<Ingredient> ingredients = recipe.getIngredients();
        if(ingredients == null || ingredients.isEmpty()) {
            return null;
        }

        return ingredients.stream()
                    .filter(ingredient -> ingredient.getId().equals(id))
                    .map(ingredientToIngredientCommand::convert)
                    .findFirst()
                    .orElse(null);
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if(!recipeOptional.isPresent()){

            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUnitOfMeasure(unitOfMeasureRepository
                        .findById(command.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                //add new Ingredient
                recipe.addIngredient(ingredientCommandToIngredient.convert(command));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            //to do check for fail
            return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst()
                    .get());
        }

    }
}
