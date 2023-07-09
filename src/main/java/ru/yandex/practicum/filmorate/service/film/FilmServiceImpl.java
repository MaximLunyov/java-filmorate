package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.film.FilmStorage;
import ru.yandex.practicum.filmorate.dao.like.LikeStorage;
import ru.yandex.practicum.filmorate.dao.user.UserStorage;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class FilmServiceImpl implements FilmService{
    private FilmStorage filmStorage;
    private UserStorage userStorage;
    private LikeStorage likeStorage;

    @Autowired
    public FilmServiceImpl(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                       @Qualifier("userDbStorage") UserStorage userStorage,
                       LikeStorage likeStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.likeStorage = likeStorage;
    }

    public void likeFilm(int filmId, int userId) throws ValidationException {
        Film film = filmStorage.getFilmById(filmId);
        if (film != null) {
            if (userStorage.findUserById(userId) != null) {
                likeStorage.likeFilm(filmId, userId);
            } else {
                throw new NoSuchElementException();
            }
        } else {
            throw new NoSuchElementException();
        }
    }

    public void deleteLike(int filmId, int userId) throws ValidationException {
        Film film = filmStorage.getFilmById(filmId);
        log.info(film.toString());
        if (film != null) {
            if (film.getLikes().contains((long) userId)) {
                likeStorage.deleteLike(filmId, userId);
            } else {
                throw new NoSuchElementException();
            }
        } else {
            throw new NoSuchElementException();
        }
    }

    public List<Film> getPopular(Integer count) {
        if (count < 1) {
            new ValidationException("Количество фильмов для вывода не должно быть меньше 1");
        }
        return likeStorage.getPopular(count);
    }
}
