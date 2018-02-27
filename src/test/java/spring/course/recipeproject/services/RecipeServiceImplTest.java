package spring.course.recipeproject.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import spring.course.recipeproject.models.Recipe;
import spring.course.recipeproject.repositories.RecipeRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this); // give me a mock RecipeRepository

        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void getRecipes() {
        Recipe givenRecipe = new Recipe();
        Set<Recipe> givenRecipes = Collections.singleton(givenRecipe);

        when(recipeService.getRecipes()).thenReturn(givenRecipes);

        Set<Recipe> actualRecipes = recipeService.getRecipes();
        verify(recipeRepository, times(1)).findAll(); // verify that RecipeRepository.findAll() was call only once
    }
}