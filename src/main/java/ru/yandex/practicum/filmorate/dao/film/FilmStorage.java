package ru.yandex.practicum.filmorate.dao.film;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    public List<Film> findAllFilms();

    public Film createFilm(Film film) throws ValidationException;

    public Film updateFilm(Film film) throws ValidationException;

    Film getFilmById(int filmId) throws ValidationException;

    Film delete(int filmId) throws ValidationException;
}
