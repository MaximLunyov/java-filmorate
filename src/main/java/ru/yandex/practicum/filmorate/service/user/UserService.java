package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {

    public List<User> findAllUsers();

    public User findUserById(int id);

    public User createUser(User user);

    public User updateUser(User user) throws ValidationException;

    public User addFriend(int id, int friendId);

    public User deleteFriend(int id, int friendId);

    public List<User> getUserFriends(int id);

    public List<User> getCommonFriends(int id, int otherId);
}
