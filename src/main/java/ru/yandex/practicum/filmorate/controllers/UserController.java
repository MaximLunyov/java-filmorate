package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private Map<Integer, User> userMap = new HashMap();
    protected int id = 0;

    @GetMapping
    public List<User> userList() {
        return new ArrayList<>(userMap.values());
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Получен запрос на добавление пользователя");
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(++id);
        userMap.put(user.getId(), user);

        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) throws ValidationException {
        log.info("Получен запрос на обновление пользователя");
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (userMap.containsKey(user.getId())) {
            userMap.put(user.getId(), user);
        } else {
            throw new ValidationException("Пользователь с указанным id не найден");
        }
        return user;
    }
}
