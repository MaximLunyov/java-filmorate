package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> userList() {
        log.info("Получен запрос на получение списка пользователей");
        return userService.findAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        log.info("Получен запрос на добавление пользователя с id: " + id);
        if (id <= 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return userService.findUserById(id);
    }

    @PostMapping("/users")
    public User create(@Valid @RequestBody User user) {
        log.info("Получен запрос на добавление пользователя: " + user);
        return userService.createUser(user);
    }

    @PutMapping("/users")
    public User update(@Valid @RequestBody User user) throws ValidationException {
        log.info("Получен запрос на обновление пользователя: " + user.getLogin());
        return userService.updateUser(user);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public User addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен запрос на добавление друга пользователя c id: " + id);
        if (friendId <= 0 || id <= 0) {
            throw new NoSuchElementException("Id пользователя не может быть отрицательным!");
        }
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен запрос на удаление друга пользователя c id: " + id);
        return userService.deleteFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getUserFriends(@PathVariable int id) {
        log.info("Получен запрос на получение списка друзей пользователя c id: " + id);
        return userService.getUserFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info("Получен запрос на получение списка общих друзей пользователей c id: " + id + " и " + otherId);
        return userService.getCommonFriends(id, otherId);
    }

}
