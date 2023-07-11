package ru.yandex.practicum.filmorate.dao.friend;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendStorage {
    public void addFriend(User user, User friend, int userId, int friendId);

    public void deleteFriend(User user, User friend, int userId, int friendId);

    public List<User> getFriends(User user, int userId);
}
