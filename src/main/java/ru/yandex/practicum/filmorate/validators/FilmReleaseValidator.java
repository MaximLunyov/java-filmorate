package ru.yandex.practicum.filmorate.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class FilmReleaseValidator implements ConstraintValidator<FilmRelease, LocalDate> {

    @Override
    public void initialize(FilmRelease constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate notBefore = LocalDate.of(1895, 12, 28);
        return !localDate.isBefore(notBefore);
    }
}
