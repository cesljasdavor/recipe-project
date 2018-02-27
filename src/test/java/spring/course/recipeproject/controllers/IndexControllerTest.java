package spring.course.recipeproject.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import spring.course.recipeproject.models.Recipe;
import spring.course.recipeproject.services.RecipeService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IndexControllerTest {

    @Mock
    private RecipeService recipeService;

    @Mock
    private Model model;

    private IndexController indexController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    public void testMockMVC() throws Exception { // Pretty important stuff. Makes it a lot easier to unit test MVC Controllers without having to start the server
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build(); // use webAppContextSetup for integration tests

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void getIndexPage() {
        // given
        Recipe givenRecipe1 = new Recipe();
        givenRecipe1.setId(1L);
        Recipe givenRecipe2 = new Recipe();
        givenRecipe2.setId(2L);
        Set<Recipe> givenRecipes = new HashSet<>(Arrays.asList(givenRecipe1, givenRecipe2));

        String givenViewName = "index";

        // when
        when(recipeService.getRecipes()).thenReturn(givenRecipes);

        // then
        String actualViewName = indexController.getIndexPage(model);
        assertEquals(givenViewName, actualViewName);

        verify(recipeService, times(1)).getRecipes();

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
        verify(model, times(1)).addAttribute(
                eq("recipes"),
                argumentCaptor.capture()
        );
        Set<Recipe> actualRecipes = argumentCaptor.getValue();
        assertEquals(2, actualRecipes.size());
        assertEquals(givenRecipes, actualRecipes);
    }
}