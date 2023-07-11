package ru.yandex.practicum.filmorate.dao.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Slf4j
@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> findAllFilms() {
        return jdbcTemplate.query("SELECT * FROM films", (rs, rowNum) -> new Film(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("release_Date").toLocalDate(),
                rs.getInt("duration"),
                new Mpa(rs.getInt("rating_id"), "tested"),
                new HashSet<>())
        );
    }

    @Override
    public Film createFilm(Film film, Mpa mpa, Set<Genre> genres) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");
        film.setId(simpleJdbcInsert.executeAndReturnKey(film.toMap()).intValue());

        return film;
    }

    @Override
    public Film updateFilm(Film film, Mpa mpa, Set<Genre> genres) throws ValidationException {
        if (film == null) {
            throw new ValidationException("Передан пустой аргумент!");
        }
        if (jdbcTemplate.update("UPDATE films SET name = ?, description = ?, " +
                        "release_date = ?, duration = ?, rating_id = ? WHERE id = ?",
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId()) != 0) {

            film.setMpa(mpa);

            return film;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Film getFilmById(int filmId, Mpa mpa, Set<Genre> genres) {
        Film film;
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("SELECT * FROM films WHERE id = ?", filmId);
        if (filmRows.first()) {
            film = new Film(
                    filmRows.getInt("id"),
                    filmRows.getString("name"),
                    filmRows.getString("description"),
                    filmRows.getDate("release_date").toLocalDate(),
                    filmRows.getInt("duration"),
                    mpa,
                    genres);
        } else {
            throw new NoSuchElementException();
        }
        return film;
    }

    @Override
    public void delete(int filmId) throws ValidationException {
        if (jdbcTemplate.update("DELETE FROM films WHERE id = ? ", filmId) == 0) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public int getMpaId(int filmId) {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("SELECT * FROM films WHERE id = ?", filmId);
        int result = 0;
        if (filmRows.first()) {
            result = filmRows.getInt("rating_id");
        }
        return result;
    }
}