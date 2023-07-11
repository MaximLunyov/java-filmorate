package ru.yandex.practicum.filmorate.dao.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Component
public class FriendDbStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addFriend(User user, User friend, int userId, int friendId) {
        if ((user != null) && (friend != null)) {
            boolean status = false;
            if (friend.getFriends().contains(userId)) {
                status = true;
                jdbcTemplate.update("UPDATE friends SET user_id = ? AND friend_id = ? AND status = ? " +
                        "WHERE user_id = ? AND friend_id = ?", friendId, userId, true, friendId, userId);
            }
            jdbcTemplate.update("INSERT INTO friends (user_id, friend_id, status) VALUES (?, ?, ?)", userId, friendId, status);
        }
    }

    public void deleteFriend(User user, User friend, int userId, int friendId) {
        if ((user != null) && (friend != null)) {
            jdbcTemplate.update("DELETE FROM friends WHERE user_id = ? AND friend_id = ?", userId, friendId);
            if (friend.getFriends().contains(userId)) {
                jdbcTemplate.update("UPDATE friends SET user_id = ? AND friend_id = ? AND status = ? " +
                        "WHERE user_id = ? AND friend_id = ?", friendId, userId, false, friendId, userId);
            }
        }
    }

    public List<User> getFriends(User user, int userId) {
        if (user != null) {
            return jdbcTemplate.query("SELECT friend_id, email, login, name, birthday FROM friends" +
                            " INNER JOIN users ON friends.friend_id = users.id WHERE friends.user_id = ?",
                            (rs, rowNum) -> new User(
                            rs.getInt("friend_id"),
                            rs.getString("email"),
                            rs.getString("login"),
                            rs.getString("name"),
                            rs.getDate("birthday").toLocalDate(),
                            null),
                    userId
            );
        } else {
            return null;
        }
    }
}