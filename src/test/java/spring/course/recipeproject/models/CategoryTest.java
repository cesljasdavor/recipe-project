package spring.course.recipeproject.models;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class CategoryTest {

    Category category;

    @Before
    public void setUp() {
        category = new Category();
    }

    @Test
    public void getId() {
        Long givenId = 4L;

        category.setId(givenId);

        assertEquals(givenId, category.getId());
    }

    @Test
    public void getCategoryName() {
        String givenCategoryName = "Category Test";

        category.setCategoryName(givenCategoryName);

        assertEquals(givenCategoryName, category.getCategoryName());
    }

    @Test
    public void getRecipes() {
        Set<Recipe> givenRecipes = Collections.singleton(new Recipe());

        category.setRecipes(givenRecipes);

        assertEquals(givenRecipes, category.getRecipes());
    }
}