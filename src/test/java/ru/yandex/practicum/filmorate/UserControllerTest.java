/*
package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
public class UserControllerTest {

    Validator validator;
    private UserController userController;

    @BeforeEach
    void beforeEach() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        userController = new UserController();
    }

    @Test
    void shouldCreateUser() {
        User user = userController.create(new User("F@LLEN@NGEL333", "Ваня", "example1@gmail.com",
                LocalDate.of(1980,1,26)));
        assertEquals(1, user.getId());
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
    void shouldSetNameIfEmpty() {
        User user = userController.create(new User("F@LLEN@NGEL333", "", "example1@gmail.com",
                LocalDate.of(1980,1,26)));
        assertEquals(user.getLogin(), user.getName());
    }

    @Test
    void createWrongEmailUser() {
        User user = userController.create(new User("F@LLEN@NGEL333",
                "Ваня", "example1gmail.com", LocalDate.of(1980,1,26)));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void createWrongLoginUser() {
        User user = userController.create(new User("F@LLEN @NGEL333",
                "Ваня", "example1@gmail.com", LocalDate.of(1980,1,26)));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void createWrongBirthdayUser() {
        User user = userController.create(new User("F@LLEN @NGEL333",
                "Ваня", "example1@gmail.com", LocalDate.of(2980,1,26)));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

}
*/
