package spring.course.recipeproject.models;

import lombok.*;

import javax.persistence.*;

/**
 * Created by cesljasdavor 25.02.18.
 */
@Data
@EqualsAndHashCode(exclude = "recipe")
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;

    @Lob // For large objects - CLOB
    private String recipeNotes;

}
