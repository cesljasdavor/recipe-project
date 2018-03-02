package spring.course.recipeproject.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.course.recipeproject.commands.UnitOfMeasureCommand;
import spring.course.recipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import spring.course.recipeproject.repositories.UnitOfMeasureRepository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by cesljasdavor 02.03.18.
 */
@Slf4j
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private UnitOfMeasureRepository unitOfMeasureRepository;
    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @Autowired
    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUnitOfMeasures() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                            .map(unitOfMeasureToUnitOfMeasureCommand::convert)
                            .collect(Collectors.toSet());
    }
}
