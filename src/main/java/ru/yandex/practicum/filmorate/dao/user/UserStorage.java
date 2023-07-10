package ru.yandex.practicum.filmorate.dao.user;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    public List<User> findAllUsers();

    public User findUserById(int id);

    public User createUser(User user);

    public User updateUser(User user) throws ValidationException;

    User delete(int userId);
}
