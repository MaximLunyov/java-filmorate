package ru.yandex.practicum.filmorate.dao.film;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Set;

public interface FilmStorage {
    public List<Film> findAllFilms();

    public Film createFilm(Film film, Mpa mpa, Set<Genre> genres) throws ValidationException;

    public Film updateFilm(Film film, Mpa mpa, Set<Genre> genres) throws ValidationException;

    Film getFilmById(int filmId, Mpa mpa, Set<Genre> genres) throws ValidationException;

    public void delete(int filmId) throws ValidationException;

    public int getMpaId(int filmId);
}
