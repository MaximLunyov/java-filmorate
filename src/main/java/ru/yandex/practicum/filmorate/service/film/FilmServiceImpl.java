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
        log.info("Получен запрос на получения списка фильмов");
        return filmStorage.findAllFilms();
    }

    @Override
    public Film getFilmById(int id) {
        return filmStorage.getFilmById(id);
    }

    @Override
    public Film createFilm(Film film) {
        log.info("Получен запрос на добавление фильма");
        return filmStorage.createFilm(film);
    }

    @Override
    public Film updateFilm(Film film) throws ValidationException {
        log.info("Получен запрос на обновление фильма");
        return filmStorage.updateFilm(film);
    }

    @Override
    public Film likeFilm(int id, int otherId) {
        log.info("Получен запрос установка лайка на фильм с id:" + id);
        return filmStorage.likeFilm(id, otherId);
    }

    @Override
    public Film deleteLike(int id, int otherId) {
        log.info("Получен запрос на удаление лайка с фильма с id: " + id);
        return filmStorage.deleteLike(id, otherId);
    }

    @Override
    public List<Film> getMostPopular(String count) {
        if (count != null) {
            log.info("Получен запрос на получение " + count + " популярных фильмов.");
        } else {
            log.info("Получен запрос на получение 10 популярных фильмов.");
        }

        return filmStorage.getMostPopular(count);
    }
}
