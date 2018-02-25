package spring.course.recipeproject.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by cesljasdavor 25.02.18.
 */
@Data
@EqualsAndHashCode(exclude = "recipes")
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;

    @ManyToMany(mappedBy = "categories") // So the table category_recipes doesn't get created
    private Set<Recipe> recipes;

}
