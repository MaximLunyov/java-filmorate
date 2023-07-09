package ru.yandex.practicum.filmorate.dao.friend;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendStorage {
    public void addFriend(int userId, int friendId);

    public void deleteFriend(int userId, int friendId);

    public List<User> getFriends(int userId);
}
