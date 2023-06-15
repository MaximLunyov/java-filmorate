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
        log.info("Получен запрос на добавление пользователя");
        return userStorage.createUser(user);
    }

    @Override
    public User updateUser(User user) throws ValidationException {
        log.info("Получен запрос на обновление пользователя: " + user.getLogin());
        return userStorage.updateUser(user);
    }

    @Override
    public User addFriend(int id, int friendId) {
        log.info("Получен запрос на добавление друга пользователя c id: " + id);
        return userStorage.addFriend(id, friendId);
    }

    @Override
    public User deleteFriend(int id, int friendId) {
        log.info("Получен запрос на удаление друга пользователя c id: " + id);
        return userStorage.deleteFriend(id, friendId);
    }

    @Override
    public List<User> getUserFriends(int id) {
        log.info("Получен запрос на получение списка друзей пользователя c id: " + id);
        return userStorage.getUserFriends(id);
    }

    @Override
    public List<User> getCommonFriends(int id, int otherId) {
        log.info("Получен запрос на получение списка общих друзей пользователей c id: " + id + " и " + otherId);
        return userStorage.getCommonFriends(id, otherId);
    }
}
