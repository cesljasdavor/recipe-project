package spring.course.recipeproject.services;

import spring.course.recipeproject.commands.UnitOfMeasureCommand;

import java.util.Set;

/**
 * Created by cesljasdavor 02.03.18.
 */
public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAllUnitOfMeasures();
}
