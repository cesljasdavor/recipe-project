package spring.course.recipeproject.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.course.recipeproject.commands.RecipeCommand;
import spring.course.recipeproject.exceptions.NotFoundException;
import spring.course.recipeproject.models.Recipe;
import spring.course.recipeproject.services.RecipeService;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    RecipeController recipeController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        recipeController = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new ControllerExceptionHandler()) // Without this test will fail, because we didn't bring up whole context
                .build();
    }

    @Test
    public void getRecipe() throws Exception {
        Long givenId = 1L;
        Recipe givenRecipe = new Recipe();
        givenRecipe.setId(givenId);

        when(recipeService.findById(anyLong())).thenReturn(givenRecipe);

        mockMvc.perform(get("/recipe/" + givenId + "/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void getRecipeNotFound() throws Exception {
        when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipe/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(model().attributeExists("status"))
                .andExpect(model().attributeExists("ex"))
                .andExpect(view().name("error"));
    }

    @Test
    public void handleRecipeIdFormatException() throws Exception {
        mockMvc.perform(get("/recipe/dsasdsa/show"))
                .andExpect(status().isBadRequest())
                .andExpect(model().attributeExists("status"))
                .andExpect(model().attributeExists("ex"))
                .andExpect(view().name("error"));
    }

    @Test
    public void getNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();

        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void postNewRecipeForm() throws Exception {
        Long givenId = 2L;
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(givenId);

        when(recipeService.saveRecipeCommand(any(RecipeCommand.class))).thenReturn(recipeCommand);

        mockMvc.perform(createPost())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + givenId + "/show"));
    }

    private MockHttpServletRequestBuilder createPost() {
        return post("/recipe")
                .contentType(APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "My recipe")
                .param("directions", "Some directions");
    }

    @Test
    public void postNewRecipeFormValidationFail() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        mockMvc.perform(createFailingPost())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipe/recipeform"));
    }

    private RequestBuilder createFailingPost() {
        return post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("cookTime", "3000");
    }

    @Test
    public void getUpdateView() throws Exception {
        Long givenId = 2L;
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(givenId);

        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/" + givenId + "/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void deleteRecipe() throws Exception {
        Long givenId = 2L;

        mockMvc.perform(get("/recipe/" + givenId + "/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));

        verify(recipeService, times(1)).deleteById(anyLong());
    }
}