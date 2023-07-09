package ru.yandex.practicum.filmorate.service.film;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FilmServiceImpl implements FilmService {
    public final FilmStorage filmStorage;

    @Override
    public List<Film> findAllFilms() {
        return filmStorage.findAllFilms();
    }

    @Override
    public Film getFilmById(int id) {
        return filmStorage.getFilmById(id);
    }

    @Override
    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    @Override
    public Film updateFilm(Film film) throws ValidationException {
        return filmStorage.updateFilm(film);
    }

    @Override
    public Film likeFilm(int id, int otherId) {
        return filmStorage.likeFilm(id, otherId);
    }

    @Override
    public Film deleteLike(int id, int otherId) {
        return filmStorage.deleteLike(id, otherId);
    }

    @Override
    public List<Film> getMostPopular(int count) {
        return filmStorage.getMostPopular(count);
    }
}
