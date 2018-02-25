package spring.course.recipeproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.course.recipeproject.models.Category;
import spring.course.recipeproject.models.UnitOfMeasure;
import spring.course.recipeproject.repositories.CategoryRepository;
import spring.course.recipeproject.repositories.UnitOfMeasureRepository;
import spring.course.recipeproject.services.RecipeService;

import java.util.Optional;

/**
 * Created by cesljasdavor 23.02.18.
 */
@Controller
public class IndexController {

    private final RecipeService recipeService;

    @Autowired
    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}
