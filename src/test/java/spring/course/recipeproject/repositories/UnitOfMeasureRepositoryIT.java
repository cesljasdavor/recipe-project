package spring.course.recipeproject.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import spring.course.recipeproject.models.UnitOfMeasure;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
//    @DirtiesContext would reload context after this test
    public void findByDescriptionTeaspoon() {
        String givenDescription = "Teaspoon";

        Optional<UnitOfMeasure> optionalUnitOfMeasure = unitOfMeasureRepository.findByDescription(givenDescription);

        assertEquals(givenDescription, optionalUnitOfMeasure.get().getDescription());
    }

    @Test
    public void findByDescriptionCup() {
        String givenDescription = "Cup";

        Optional<UnitOfMeasure> optionalUnitOfMeasure = unitOfMeasureRepository.findByDescription(givenDescription);

        assertEquals(givenDescription, optionalUnitOfMeasure.get().getDescription());
    }
}