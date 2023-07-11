    package ru.yandex.practicum.filmorate.dao.user;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
    import org.springframework.jdbc.support.rowset.SqlRowSet;
    import org.springframework.stereotype.Component;
    import ru.yandex.practicum.filmorate.model.User;

    import java.util.List;
    import java.util.NoSuchElementException;

    @Slf4j
    @Component("userDbStorage")
    public class UserDbStorage implements UserStorage {

        private final JdbcTemplate jdbcTemplate;

        @Autowired
        public UserDbStorage(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        @Override
        public List<User> findAllUsers() {
            String sql = "SELECT * FROM users";
            return jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("login"),
                    rs.getString("name"),
                    rs.getDate("birthday").toLocalDate(),
                    null)
            );
        }

        @Override
        public User createUser(User user) {
            SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("users")
                    .usingGeneratedKeyColumns("id");
            user.setId(simpleJdbcInsert.executeAndReturnKey(user.toMap()).intValue());
            return user;
        }

        @Override
        public User updateUser(User user) {
            if (findUserById(user.getId()) != null) {
                jdbcTemplate.update("UPDATE users SET email = ?, login = ?, name = ?, birthday = ? WHERE id = ?",
                        user.getEmail(),
                        user.getLogin(),
                        user.getName(),
                        user.getBirthday(),
                        user.getId());
                return user;
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public User findUserById(int userId) {
            User user;
            SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT * FROM users WHERE id = ?", userId);
            if (userRows.first()) {
                user = new User(
                        userRows.getInt("id"),
                        userRows.getString("email"),
                        userRows.getString("login"),
                        userRows.getString("name"),
                        userRows.getDate("birthday").toLocalDate(),
                        null);
            } else {
                throw new NoSuchElementException();
            }
            return user;
        }

        @Override
        public User delete(int userId) {
            User user = findUserById(userId);
            if (jdbcTemplate.update("DELETE FROM users WHERE id = ? ", userId) == 0) {
                throw new NoSuchElementException();
            }
            return user;
        }
    }