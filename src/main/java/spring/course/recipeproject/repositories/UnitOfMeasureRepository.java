package spring.course.recipeproject.repositories;

import org.springframework.data.repository.CrudRepository;
import spring.course.recipeproject.models.UnitOfMeasure;

import java.util.Optional;

/**
 * Created by cesljasdavor 25.02.18.
 */
public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {
    Optional<UnitOfMeasure> findByDescription(String description);
}
