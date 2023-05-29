package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controllers.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class FilmControllerTest {

    private FilmController filmController;

    @BeforeEach
    void beforeEach() {
        filmController = new FilmController();
    }

    @Test
    void shouldCreateFilm() throws ValidationException {
        Film film = filmController.create(new Film("Аватар", "бла-бла",
                LocalDate.of(1980,1,26), 256));
        assertNotNull(film.getId());
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
    void shouldReturnFilmList() throws ValidationException {
        filmController.create(new Film("Аватар", "бла-бла",
                LocalDate.of(1980,1,26), 256));
        filmController.create(new Film("Дамбо", "бла-бла-бла",
                LocalDate.of(1995,8,15), 401));
        assertEquals(2, filmController.filmList().size());
    }

    @Test
    void shouldThrowsValidationExceptionIfEmptyName() {
        assertThrows(ValidationException.class, () -> filmController.create(new Film("","",
                LocalDate.of(2000,1,1), 259)));
    }

    @Test
    void shouldThrowsValidationExceptionIfBigDescription() {
        assertThrows(ValidationException.class, () -> filmController.create(new Film("Аватар","ApplicationEventsTestExecutionListener, org.springframework.test.context.support.DependencyInjectionTestExecutionListener, org.springframework.test.context.support.DirtiesContextTestExecutionListener, org.springframework.test.context.transaction.TransactionalTestExecutionListener, org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener, org.springframework.test.context.event.EventPublishingTestExecutionListener]\n" +
                "12:01:26.435 [main] DEBUG org.springframework.boot.test.context.SpringBootTestContextBootstrapper - Skipping candidate TestExecutionListener [org.springframework.test.context.transaction.TransactionalTestExecutionListener] due to a missing dependency. Specify custom listener classes or make the default listener classes and their required dependencies available. Offending class: [org/springframework/transaction/interceptor/TransactionAttributeSource]\n" +
                "12:01:26.436 [main] DEBUG org.springframework.boot.test.context.SpringBootTestContextBootstrapper - Skipping candidate TestExecutionListener [org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener] due to a missing dependency. Specify custom listener classes or make the default listener classes and their required dependencies available. Offending class: [org/springframework/transaction/interceptor/TransactionAttribute]\n" +
                "12:01:26.436 [main] INFO org.springframework.boot.test.context.SpringBootTestContextBootstrapper - Using TestExecutionListeners: [org.springframework.test.context.web.ServletTestExecutionListener@39a8312f, org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener@5f6722d3, org.springframework.test.context.event.ApplicationEventsTestExecutionListener@2c532cd8, org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener@294e5088, org.springframework.boot.test.autoconfigure.SpringBootDependencyInjectionTestExecutionListener@51972dc7, org.springframework.test.context.support.DirtiesContextTestExecutionListener@3700ec9c, org.springframework.test.context.event.EventPublishingTestExecutionListener@2002348, org.springframework.boot.test.mock.mockito.ResetMocksTestExecutionListener@5911e990, org.springframework.boot.test.autoconfigure.restdocs.RestDocsTestExecutionListener@31000e60, org.springframework.boot.test.autoconfigure.web.client.MockRestServiceServerResetTestExecutionListener@1d470d0, org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrintOnlyOnFailureTestExecutionListener@24d09c1, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverTestExecutionListener@54c62d71, org.springframework.boot.test.autoconfigure.webservices.client.MockWebServiceServerTestExecutionListener@65045a87]\n" +
                "12:01:26.442 [main] DEBUG org.springframework.test.context.support.AbstractDirtiesContextTestExecutionListener - Befor",
                LocalDate.of(2000,1,1), 259)));
    }

    @Test
    void shouldThrowsValidationExceptionIfWrongDate() {
        assertThrows(ValidationException.class, () -> filmController.create(new Film("Титаник","",
                LocalDate.of(1895,12,27), 259)));
    }

    @Test
    void shouldThrowsValidationExceptionIfWrongDuration() {
        assertThrows(ValidationException.class, () -> filmController.create(new Film("Титаник","",
                LocalDate.of(1896,12,27), -1)));
    }
}
