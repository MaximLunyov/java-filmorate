package ru.yandex.practicum.filmorate.service.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    public final UserStorage userStorage;

    @Override
    public List<User> findAllUsers() {
        return userStorage.findAllUsers();
    }

    @Override
    public User findUserById(int id) {
        return userStorage.findUserById(id);
    }

    @Override
    public User createUser(User user) {
        return userStorage.createUser(addName(user));
    }

    @Override
    public User updateUser(User user) throws ValidationException {
        return userStorage.updateUser(addName(user));
    }

    @Override
    public User addFriend(int id, int friendId) {
        return userStorage.addFriend(id, friendId);
    }

    @Override
    public User deleteFriend(int id, int friendId) {
        return userStorage.deleteFriend(id, friendId);
    }

    @Override
    public List<User> getUserFriends(int id) {
        return userStorage.getUserFriends(id);
    }

    @Override
    public List<User> getCommonFriends(int id, int otherId) {
        return userStorage.getCommonFriends(id, otherId);
    }

    private User addName(User user) {
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return user;
    }
}
