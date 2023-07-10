package ru.yandex.practicum.filmorate.service.genre;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Set;

public interface GenreService {

    public Collection<Genre> getGenres();

    public Genre getGenreById(Integer id) throws ValidationException;

    public void putGenres(Film film);

    public Set<Genre> getFilmGenres(int filmId);
}
