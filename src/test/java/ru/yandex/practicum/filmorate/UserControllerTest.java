package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserControllerTest {

    private UserController userController;

    @BeforeEach
    void beforeEach() {
        userController = new UserController();
    }

    @Test
    void shouldCreateUser() throws ValidationException {
        User user = userController.create(new User("F@LLEN@NGEL333", "Ваня", "example1@gmail.com",
                LocalDate.of(1980,1,26)));
        assertNotNull(user.getId());
    }

    @Test
    void shouldUpdateUser() throws ValidationException {
        User user1 = userController.create(new User("F@LLEN@NGEL333", "Ваня", "example1@gmail.com",
                LocalDate.of(1980,1,26)));
        User user2 = new User("Преисполнившийся", "Иван",
                "example1@gmail.com", LocalDate.of(1980,1,26));
        user2.setId(user1.getId());
        User user3 = userController.update(user2);

        assertEquals(1, userController.userList().size());
        assertEquals(user3, userController.userList().get(0));
    }

    @Test
    void shouldThrowsValidationExceptionIfWrongEmail() {
        assertThrows(ValidationException.class, () -> userController.create(new User("F@LLEN@NGEL333",
                "Ваня", "example1gmail.com", LocalDate.of(1980,1,26))));
        assertThrows(ValidationException.class, () -> userController.create(new User("F@LLEN@NGEL333",
                "Ваня", " ", LocalDate.of(1980,1,26))));
        assertThrows(ValidationException.class, () -> userController.create(new User("F@LLEN@NGEL333",
                "Ваня", "", LocalDate.of(1980,1,26))));
    }

    @Test
    void shouldThrowsValidationExceptionIfWrongLogin() {
        assertThrows(ValidationException.class, () -> userController.create(new User("F@LLEN @NGEL333",
                "Ваня", "example1gmail.com", LocalDate.of(1980,1,26))));
        assertThrows(ValidationException.class, () -> userController.create(new User("F@LLEN@NGEL333 ",
                "Ваня", "example1gmail.com", LocalDate.of(1980,1,26))));
        assertThrows(ValidationException.class, () -> userController.create(new User(" ",
                "Ваня", "example1gmail.com", LocalDate.of(1980,1,26))));
        assertThrows(ValidationException.class, () -> userController.create(new User("",
                "Ваня", "example1gmail.com", LocalDate.of(1980,1,26))));
    }

    @Test
    void shouldThrowsValidationExceptionIfWrongBirthDay() {
        assertThrows(ValidationException.class, () -> userController.create(new User("F@LLEN@NGEL333",
                "Ваня", "example1gmail.com", LocalDate.of(2880,1,26))));
    }

    @Test
    void shouldSetNameIfEmpty() throws ValidationException {
        User user = userController.create(new User("F@LLEN@NGEL333", "", "example1@gmail.com",
                LocalDate.of(1980,1,26)));
        assertEquals(user.getLogin(), user.getName());
    }
}
