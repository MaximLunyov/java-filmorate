package ru.yandex.practicum.filmorate.dao.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component("inMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {

    private Map<Integer, User> userMap = new HashMap();
    protected int id = 0;

    @Override
    public List<User> findAllUsers() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public User findUserById(int id) {
        if (!userMap.containsKey(id)) {
            throw new NoSuchElementException("Пользователь с указанным id не найден");
        }
        return userMap.get(id);
    }

    @Override
    public User createUser(User user) {
        user.setId(++id);
        userMap.put(user.getId(), user);

        return user;
    }

    @Override
    public User updateUser(User user) {
        if (userMap.containsKey(user.getId())) {
            userMap.put(user.getId(), user);
        } else {
            throw new NoSuchElementException("Пользователь с указанным id не найден");
        }
        return user;
    }

    @Override
    public User delete(int userId) {
        return null;
    }
}
