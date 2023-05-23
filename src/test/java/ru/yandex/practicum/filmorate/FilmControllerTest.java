package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


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
    void shouldReturnFilmIfCorrectName() throws ValidationException {
        assertEquals(film1 , FilmController.validation(film1));
    }

    @Test
    void shouldReturnEmptyUserIfIncorrectName() throws ValidationException {
        film1.setName("");
        assertNull(FilmController.validation(film1));
    }

    @Test
    void shouldReturnUserIfCorrectDescriptionLength() throws ValidationException {
        assertEquals(film1 ,FilmController.validation(film1));
    }

    @Test
    void shouldReturnEmptyUserIfIncorrectDescriptionLength() throws ValidationException {
        film1.setDescription("Кто бы ты ни был, мой читатель, на каком бы месте ни стоял, в каком бы звании ни находился," +
                " почтен ли ты высшим чином или человек простого сословия, но если тебя вразумил Бог грамоте и попалась " +
                "уже тебе в руки моя книга, я прошу тебя помочь мне.\n" +
                "В книге, которая перед тобой, которую, вероятно, ты уже прочел в ее первом издании, изображен " +
                "человек, взятый из нашего же государства. Ездит он по нашей Русской земле, встречается с людьми " +
                "всяких сословий, от благородных до простых. Взят он больше затем, чтобы показать недостатки и пороки" +
                " русского человека, а не его достоинства и добродетели, и все люди, которые окружают его, взяты " +
                "также затем, чтобы показать наши слабости и недостатки; лучшие люди и характеры будут в других частях. " +
                "В книге этой многое описано неверно, не так, как есть и как действительно происходит в Русской земле," +
                " потому что я не мог узнать всего: мало жизни человека на то, чтобы узнать одному и сотую часть того," +
                " что делается в нашей земле. Притом от моей собственной оплошности, незрелости и поспешности произошло" +
                " множество всяких ошибок и промахов, так что на всякой странице есть что поправить: я прошу тебя," +
                " читатель, поправить меня. Не пренебреги таким делом. Какого бы ни был ты сам высокого образования и " +
                "жизни высокой, и какою бы ничтожною ни показалась в глазах твоих моя книга, и каким бы ни показалось" +
                " тебе мелким делом ее исправлять и писать на нее замечания, — я прошу тебя это сделать. А ты, читатель" +
                " невысокого образования и простого звания, не считай себя таким невежею, чтобы ты не мог меня" +
                " чему-нибудь поучить. Всякий человек, кто жил и видел свет и встречался с людьми, заметил что-нибудь" +
                " такое, чего другой не заметил, и узнал что-нибудь такое, чего другие не знают. А потому не лиши меня" +
                " твоих замечаний: не может быть, чтобы ты не нашелся чего-нибудь сказать на какое-нибудь место во всей" +
                " книге, если только внимательно прочтешь ее.");
        assertNull(FilmController.validation(film1));
    }

    @Test
    void shouldReturnUserIfCorrectReleaseDate() throws ValidationException {
        film1.setReleaseDate(LocalDate.of(1895,12,29));
        assertEquals(film1 ,FilmController.validation(film1));
    }

    @Test
    void shouldReturnEmptyUserIfIncorrectReleaseDate() throws ValidationException {
        film1.setReleaseDate(LocalDate.of(1895,12,27));
        assertNull(FilmController.validation(film1));
    }

    @Test
    void shouldReturnUserIfCorrectDuration() throws ValidationException {
        assertEquals(film1, FilmController.validation(film1));
    }

    @Test
    void shouldNotReturnUserIfCorrectDuration() throws ValidationException {
        film1.setDuration(Duration.ofSeconds(-1));
        assertNull(FilmController.validation(film1));
    }
}
