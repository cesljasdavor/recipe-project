package spring.course.recipeproject.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.course.recipeproject.commands.RecipeCommand;
import spring.course.recipeproject.exceptions.NotFoundException;
import spring.course.recipeproject.services.RecipeService;

import javax.validation.Valid;

/**
 * Created by cesljasdavor 01.03.18.
 */
@Slf4j
@Controller
@RequestMapping("recipe")
public class RecipeController {
    private RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("{id}/show")
    public String getRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findById(id));

        return "recipe/show";
    }

    @RequestMapping("new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @PostMapping("")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult) { // Map the form POST params to RecipeCommand
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> log.debug(error.toString()));

            return "recipe/recipeform";
        }

        RecipeCommand persistedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:" + persistedCommand.getId() + "/show"; // Tells Spring MVC to redirect to this URL. Forms a request to it.
    }

    @RequestMapping("{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id));

        return "recipe/recipeform";
    }

    @RequestMapping("{id}/delete")
    public String deleteRecipe(@PathVariable Long id) {
        log.debug("Deleting recipe with id: " + id);

        recipeService.deleteById(id);

        return "redirect:/index";
    }

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleRecipeNotFound(Exception ex) {
        return ControllerExceptionHandler.handleError(ex, HttpStatus.NOT_FOUND);
    }
}
