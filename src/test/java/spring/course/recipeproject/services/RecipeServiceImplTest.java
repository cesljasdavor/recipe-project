package spring.course.recipeproject.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import spring.course.recipeproject.converters.RecipeCommandToRecipe;
import spring.course.recipeproject.converters.RecipeToRecipeCommand;
import spring.course.recipeproject.models.Recipe;
import spring.course.recipeproject.repositories.RecipeRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this); // give me a mock RecipeRepository

        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipeById() {
        long givenId = 1L;
        Recipe givenRecipe = new Recipe();
        givenRecipe.setId(givenId);
        Optional<Recipe> givenOptionalRecipe = Optional.of(givenRecipe);

        when(recipeRepository.findById(anyLong())).thenReturn(givenOptionalRecipe);

        Recipe actualRecipe = recipeService.findById(givenId);
        assertNotNull("Null recipe returned", actualRecipe);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipes() {
        Recipe givenRecipe = new Recipe();
        Set<Recipe> givenRecipes = Collections.singleton(givenRecipe);

        when(recipeService.getRecipes()).thenReturn(givenRecipes);

        Set<Recipe> actualRecipes = recipeService.getRecipes();
        verify(recipeRepository, times(1)).findAll(); // verify that RecipeRepository.findAll() was call only once
        verify(recipeRepository, never()).findById(anyLong());
    }

    @Test
    public void deleteById() throws Exception {
        Long givenId = 2L;

        recipeService.deleteById(givenId);

        verify(recipeRepository, times(1)).deleteById(anyLong());
    }
}