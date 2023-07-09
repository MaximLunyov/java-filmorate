package ru.yandex.practicum.filmorate.dao.genre;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {
    public List<Genre> getGenres();

    public Genre getGenreById(Integer genreId) throws ValidationException;

    public void delete(Film film);

    public void add(Film film);

    public List<Genre> getFilmGenres(int filmId);
}
