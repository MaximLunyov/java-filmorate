package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.friend.FriendStorage;
import ru.yandex.practicum.filmorate.dao.user.UserStorage;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private UserStorage userStorage;
    private FriendStorage friendStorage;

    @Autowired
    public UserServiceImpl(@Qualifier("userDbStorage") UserStorage userStorage, FriendStorage friendStorage) {
        this.userStorage = userStorage;
        this.friendStorage = friendStorage;
    }

    public void addFriend(int userId, int friendId) throws ValidationException {
        if (userId == friendId) {
            throw new ValidationException("Нельзя добавить самого себя в друзья!");
        }
        friendStorage.addFriend(userId, friendId);
    }

    public void deleteFriend(int userId, int friendId) throws ValidationException {
        if (userId == friendId) {
            throw new ValidationException("Нельзя удалить самого себя из друзей!");
        }
        friendStorage.deleteFriend(userId, friendId);
    }

    public List<User> getFriends(int userId) {
        List<User> friends = new ArrayList<>();

        friends = friendStorage.getFriends(userId);

        return friends;
    }

    public List<User> getCommonFriends(int firstUserId, int secondUserId) {

        User firstUser = userStorage.findUserById(firstUserId);
        User secondUser = userStorage.findUserById(secondUserId);
        Set<User> intersection = null;

        if ((firstUser != null) && (secondUser != null)) {
            intersection = new HashSet<>(friendStorage.getFriends(firstUserId));
            intersection.retainAll(friendStorage.getFriends(secondUserId));
        }
        return new ArrayList<User>(intersection);
    }
}
