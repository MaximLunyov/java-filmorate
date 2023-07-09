package ru.yandex.practicum.filmorate.service.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Component
public interface FilmService {
    public List<Film> findAllFilms();

    public Film getFilmById(int id);

    public Film createFilm(Film film);

    public Film updateFilm(Film film) throws ValidationException;

    public Film likeFilm(int id, int otherId);

    public Film deleteLike(int id, int otherId);

    public List<Film> getMostPopular(int count);
}
