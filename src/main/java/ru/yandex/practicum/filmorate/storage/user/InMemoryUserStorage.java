package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Component
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

        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(++id);
        userMap.put(user.getId(), user);

        return user;
    }

    @Override
    public User updateUser(User user) {

        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (userMap.containsKey(user.getId())) {
            userMap.put(user.getId(), user);
        } else {
            throw new NoSuchElementException("Пользователь с указанным id не найден");
        }
        return user;
    }

    @Override
    public User addFriend(int id, int friendId) {
        if (userMap.containsKey(id) && userMap.containsKey(friendId) && id != friendId) {
            userMap.get(id).getFriends().add((long) friendId);
            userMap.get(friendId).getFriends().add((long) id);
        }
        return userMap.get(id);
    }

    @Override
    public User deleteFriend(int id, int friendId) {
        if (userMap.containsKey(id) && userMap.containsKey(friendId) && id != friendId) {
            userMap.get(id).getFriends().remove((long) friendId);
            userMap.get(friendId).getFriends().remove((long) id);
        }
        return userMap.get(id);
    }

    @Override
    public List<User> getUserFriends(int id) {
        List<User> userFriends = new ArrayList<>();
        if (userMap.containsKey(id)) {
            for (Long friendId : userMap.get(id).getFriends()) {
                userFriends.add(userMap.get(Math.toIntExact(friendId)));
            }
        }
        return userFriends;
    }

    @Override
    public List<User> getCommonFriends(int id, int otherId) {
        List<User> commonFriends = new ArrayList<>();
        if (userMap.containsKey(id) && userMap.containsKey(otherId) && id != otherId) {
            for (Long friendId : userMap.get(id).getFriends()) {
                if (userMap.get(otherId).getFriends().contains(friendId)) {
                   commonFriends.add(userMap.get(Math.toIntExact(friendId)));
                }
            }
        }
        return commonFriends;
    }
}
