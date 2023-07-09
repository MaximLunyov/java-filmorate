package ru.yandex.practicum.filmorate.service.film;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {
    public void likeFilm(int filmId, int userId) throws ValidationException;

    public void deleteLike(int filmId, int userId) throws ValidationException;

    public List<Film> getPopular(Integer count);
}
