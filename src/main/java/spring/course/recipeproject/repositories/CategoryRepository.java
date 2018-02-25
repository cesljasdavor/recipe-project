package spring.course.recipeproject.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.course.recipeproject.models.Category;

import java.util.Optional;

/**
 * Created by cesljasdavor 25.02.18.
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByCategoryName(String categoryName);
}
