package spring.course.recipeproject.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.course.recipeproject.commands.RecipeCommand;
import spring.course.recipeproject.converters.RecipeCommandToRecipe;
import spring.course.recipeproject.converters.RecipeToRecipeCommand;
import spring.course.recipeproject.models.Recipe;
import spring.course.recipeproject.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by cesljasdavor 25.02.18.
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("Requiring all recipes...");

        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);

        return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        log.info("Requested recipe with id: " + id);

        return recipeRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {
        return recipeRepository.findById(id)
                            .map(recipeToRecipeCommand::convert)
                            .orElse(new RecipeCommand());
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
