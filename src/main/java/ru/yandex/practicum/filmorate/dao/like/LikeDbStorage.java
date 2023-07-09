package ru.yandex.practicum.filmorate.dao.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.genre.GenreService;
import ru.yandex.practicum.filmorate.service.mpa.MpaService;

import java.util.HashSet;
import java.util.List;


@Component("likeDbStorage")
public class LikeDbStorage implements LikeStorage{
    private final JdbcTemplate jdbcTemplate;
    private MpaService mpaService;
    private GenreService genreService;

    @Autowired
    public LikeDbStorage(JdbcTemplate jdbcTemplate, MpaService mpaService, GenreService genreService) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaService = mpaService;
        this.genreService = genreService;
    }

    public void likeFilm(int filmId, int userId) {
        jdbcTemplate.update("INSERT INTO film_likes (film_id, user_id) VALUES (?, ?)", filmId, userId);
    }

    public void deleteLike(int filmId, int userId) {
        jdbcTemplate.update("DELETE FROM film_likes WHERE film_id = ? AND user_id = ?", filmId, userId);
    }

    public List<Film> getPopular(Integer count) {
        return jdbcTemplate.query("SELECT id, name, description, release_date, duration, rating_id " +
                        "FROM films LEFT JOIN film_likes ON films.id = film_likes.film_id " +
                        "GROUP BY films.id ORDER BY COUNT(film_likes.user_id) DESC LIMIT ?", (rs, rowNum) -> {
                    try {
                        return new Film(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("description"),
                                rs.getDate("release_Date").toLocalDate(),
                                rs.getInt("duration"),
                                new HashSet<>(getLikes(rs.getInt("id"))),
                                mpaService.getMpaById(rs.getInt("rating_id")),
                                genreService.getFilmGenres(rs.getInt("id")));
                    } catch (ValidationException e) {
                        throw new RuntimeException(e);
                    }
                },
                count);
    }

    public List<Long> getLikes(int filmId) {
        return jdbcTemplate.query("SELECT user_id FROM film_likes WHERE film_id = ?",
                (rs, rowNum) -> rs.getLong("user_id"), filmId);
    }
}