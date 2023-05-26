package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RestController
public class UserController {
    private Map<Integer, User> userMap = new HashMap();
    protected int id = 0;

    @GetMapping("/users")
    public List<User> userList() {
        return new ArrayList<>(userMap.values());
    }

    @PostMapping("/user-add")
    public User create(@Valid @RequestBody User user) throws ValidationException {
        log.info("Получен запрос на добавление пользователя");
        if (validation(user)) {
            user.setId(++id);
            userMap.put(user.getId(), user);
        }
        return user;
    }

    @PatchMapping("user-update")
    public User update(@Valid @RequestBody User user) throws ValidationException {
        if (userMap.containsKey(user.getId())) {
            if (validation(user)) {
                userMap.put(user.getId(), user);
            }
        }
        return user;
    }

    public static boolean validation(User user) throws ValidationException {
        boolean test = false;
        try {
            if (!user.getEmail().contains("@") || user.getEmail().isEmpty()) {
                throw new ValidationException("Некорректно указан Email!");
            }
            Pattern pattern = Pattern.compile("\\s");
            Matcher matcher = pattern.matcher(user.getLogin());
            boolean found = matcher.find();
            if (user.getLogin().isEmpty() || found) {
                throw new ValidationException("Логин не может быть пустым или содержать пробелы!");
            }
            if (user.getBirthday().isAfter(LocalDate.now())) {
                throw new ValidationException("Дата рождения указана неверно!");
            }
            if (user.getName().isEmpty() || user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
            test = true;
        } catch (ValidationException e) {
            log.info(e.getMessage());
        }

        return test;
    }
}
