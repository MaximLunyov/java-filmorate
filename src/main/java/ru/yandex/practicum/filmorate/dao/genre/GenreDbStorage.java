package ru.yandex.practicum.filmorate.dao.genre;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Component
public class GenreDbStorage implements GenreStorage{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Genre> getGenres() {
        return jdbcTemplate.query("SELECT * FROM genres", (rs, rowNum) -> new Genre(
                rs.getInt("id"),
                rs.getString("name"))
        );
    }

    public Genre getGenreById(Integer genreId) throws ValidationException {
        if (genreId == null) {
            throw new ValidationException("Передан пустой аргумент!");
        }
        Genre genre;
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet("SELECT * FROM genres WHERE id = ?", genreId);
        if (genreRows.first()) {
            genre = new Genre(
                    genreRows.getInt("id"),
                    genreRows.getString("name")
            );
        } else {
            throw new NoSuchElementException();
        }
        return genre;
    }

    public void delete(Film film) {
        jdbcTemplate.update("DELETE FROM film_genres WHERE film_id = ?", film.getId());
    }

    public void add(Film film) {
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                jdbcTemplate.update("INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)",
                        film.getId(), genre.getId());
            }
        }
    }

    public List<Genre> getFilmGenres(int filmId) {
        List<Genre> genre = jdbcTemplate.query("SELECT genre_id, name FROM film_genres" +
                " INNER JOIN genres ON genre_id = id WHERE film_id = ?",
                (rs, rowNum) -> new Genre(
                rs.getInt("genre_id"), rs.getString("name")), filmId);
        return genre;
    }
}
