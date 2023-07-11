package ru.yandex.practicum.filmorate.service.user;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {
    public void addFriend(int userId, int friendId) throws ValidationException;

    public void deleteFriend(int userId, int friendId) throws ValidationException;

    public List<User> getFriends(int userId);

    public List<User> getCommonFriends(int firstUserId, int secondUserId);
}
