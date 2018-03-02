package spring.course.recipeproject.converters;



import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import spring.course.recipeproject.commands.CategoryCommand;
import spring.course.recipeproject.models.Category;

/**
 * Created by jt on 6/21/17.
 */
@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Synchronized // From lombok
    @Nullable
    @Override
    public Category convert(CategoryCommand source) {
        if (source == null) {
            return null;
        }

        final Category category = new Category();
        category.setId(source.getId());
        category.setCategoryName(source.getCategoryName());
        return category;
    }
}
