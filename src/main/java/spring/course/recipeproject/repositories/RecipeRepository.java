package spring.course.recipeproject.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.course.recipeproject.models.Recipe;

/**
 * Created by cesljasdavor 25.02.18.
 */
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
