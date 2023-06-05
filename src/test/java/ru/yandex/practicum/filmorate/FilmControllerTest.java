package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class FilmControllerTest {

    Validator validator;
    private FilmController filmController;

    @BeforeEach
    void beforeEach() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        filmController = new FilmController();
    }

    @Test
    void shouldCreateFilm() {
        Film film = filmController.create(new Film("Аватар", "бла-бла",
                LocalDate.of(1980,1,26), 256));
        assertEquals(1, film.getId());
    }

    @Test
    void shouldUpdateFilm() throws ValidationException {
        Film film1 = filmController.create(new Film("Аватар", "бла-бла",
                LocalDate.of(1980,1,26), 256));
        Film film2 = new Film("Аватар 2", "бла-бла x2",
                LocalDate.of(1989,4,1), 600);
        film2.setId(film1.getId());
        Film film3 = filmController.update(film2);

        assertEquals(1, filmController.filmList().size());
        assertEquals(film3, filmController.filmList().get(0));
    }

    @Test
    void shouldReturnFilmList() {
        filmController.create(new Film("Аватар", "бла-бла",
                LocalDate.of(1980,1,26), 256));
        filmController.create(new Film("Дамбо", "бла-бла-бла",
                LocalDate.of(1995,8,15), 401));
        assertEquals(2, filmController.filmList().size());
    }

    @Test
    void createEmptyNameFilm() {
        Film film = filmController.create(new Film(" ","dawdwad",
                LocalDate.of(1995,12,27), 259));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    void createWrongDescriptionFilm() {
        Film film = filmController.create(new Film("Самый лучший фильм",new String(new char[201]),
                LocalDate.of(1995,12,27), 259));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    void createWrongDateFilm() {
        Film film = filmController.create(new Film("Самый лучший фильм", "dawdaw",
                LocalDate.of(1895, 12, 27), 259));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }

    @Test
    void createWrongDurationFilm() {
        Film film = filmController.create(new Film("Самый лучший фильм", "dawdaw",
                LocalDate.of(1995, 12, 27), -1));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }
}
