package spring.course.recipeproject.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Exception handlers under advice will be applied to all @Controller annotated classes.
 * We could lower the scope!
 *
 * Created by cesljasdavor 03.03.18.
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormatException(Exception ex) {
        return handleError(ex, HttpStatus.BAD_REQUEST);
    }

    static ModelAndView handleError(Exception ex, HttpStatus status) {
        log.error(ex.getMessage());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setStatus(status);
        modelAndView.addObject("status", status);
        modelAndView.addObject("ex", ex);
        modelAndView.setViewName("error");


        return modelAndView;
    }
}
