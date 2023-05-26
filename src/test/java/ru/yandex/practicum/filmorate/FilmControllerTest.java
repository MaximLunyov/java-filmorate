package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class FilmControllerTest {

    Film film1;
    Film film2;
    Film film3;

    @BeforeEach
    void beforeEach() {
        film1 = new Film("Аватар", "бла-бла", LocalDate.of(1980,1,26), 256);
        film2 = new Film("Дамбо", "бла-бла-бла", LocalDate.of(1995,8,15), 401);
        film3 = new Film("Титаник", "бла-бла-бла-бла", LocalDate.of(2007,11,1), 51);
    }

    @Test
    void shouldReturnFalseIfCorrectName() throws ValidationException {
        assertTrue(FilmController.validation(film1));
    }

    @Test
    void shouldReturnFalseIfIncorrectName() throws ValidationException {
        film1.setName("");
        assertFalse(FilmController.validation(film1));
    }

    @Test
    void shouldReturnTrueIfCorrectDescriptionLength() throws ValidationException {
        assertTrue(FilmController.validation(film1));
    }

    @Test
    void shouldReturnFalseIfIncorrectDescriptionLength() throws ValidationException {
        film1.setDescription("Кто бы ты ни был, мой читатель, на каком бы месте ни стоял, в каком бы звании ни находился," +
                " почтен ли ты высшим чином или человек простого сословия, но если тебя вразумил Бог грамоте и попалась " +
                "уже тебе в руки моя книга, я прошу тебя помочь мне.\n" +
                "В книге, которая перед тобой, которую, вероятно, ты уже прочел в ее первом издании, изображен " +
                "человек, взятый из нашего же государства. Ездит он по нашей Русской земле, встречается с людьми " +
                "всяких сословий, от благородных до простых. Взят он больше затем, чтобы показать недостатки и пороки" +
                " русского человека, а не его достоинства и добродетели, и все люди, которые окружают его, взяты " +
                "также затем, чтобы показать наши слабости и недостатки; лучшие люди и характеры будут в других частях. ");
        assertFalse(FilmController.validation(film1));
    }

    @Test
    void shouldReturnTrueIfCorrectReleaseDate() throws ValidationException {
        film1.setReleaseDate(LocalDate.of(1895,12,29));
        assertTrue(FilmController.validation(film1));
    }

    @Test
    void shouldReturnFalseIfIncorrectReleaseDate() throws ValidationException {
        film1.setReleaseDate(LocalDate.of(1895,12,27));
        assertFalse(FilmController.validation(film1));
    }

    @Test
    void shouldReturnTrueIfCorrectDuration() throws ValidationException {
        assertTrue(FilmController.validation(film1));
    }

    @Test
    void shouldNotReturnFalseIfCorrectDuration() throws ValidationException {
        film1.setDuration(Duration.ofSeconds(-1));
        assertFalse(FilmController.validation(film1));
    }
}
