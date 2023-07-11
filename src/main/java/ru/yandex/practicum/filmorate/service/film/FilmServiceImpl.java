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
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.genre.GenreService;
import ru.yandex.practicum.filmorate.service.mpa.MpaService;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmServiceImpl implements FilmService {
    private FilmStorage filmStorage;
    private UserStorage userStorage;
    private LikeStorage likeStorage;
    private MpaService mpaService;
    private GenreService genreService;

    @Autowired
    public FilmServiceImpl(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                       @Qualifier("userDbStorage") UserStorage userStorage,
                       LikeStorage likeStorage, MpaService mpaService, GenreService genreService) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.likeStorage = likeStorage;
        this.mpaService = mpaService;
        this.genreService = genreService;
    }

    public void likeFilm(int filmId, int userId) throws ValidationException {
        Film film = filmStorage.getFilmById(filmId, new Mpa(1, "tested"), new HashSet<>());
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
        Film film = filmStorage.getFilmById(filmId, new Mpa(1, "tested"), new HashSet<>());
        User user = userStorage.findUserById(userId);
        if (film != null && user != null) {
            likeStorage.deleteLike(filmId, userId);
        } else {
            throw new NoSuchElementException();
        }
    }

    public List<Film> getPopular(Integer count) throws ValidationException {
        if (count < 1) {
            throw new ValidationException("Количество фильмов для вывода не должно быть меньше 1");
        }
        List<Film> films = likeStorage.getPopular(count);
        for (Film film : films) {
            film.setMpa(mpaService.getMpaById(film.getMpa().getId()));
            film.setGenres(genreService.getFilmGenres(film.getId()));
            log.info(film.toString());
        }
        return films;
    }

    @Override
    public List<Film> findAllFilms() throws ValidationException {
        List<Film> films = filmStorage.findAllFilms();
        for (Film film : films) {
            film.setMpa(mpaService.getMpaById(film.getMpa().getId()));
            film.setGenres(genreService.getFilmGenres(film.getId()));
        }
        return films;
    }

    @Override
    public Film getFilmById(int filmId) throws ValidationException {
        return filmStorage.getFilmById(filmId, mpaService.getMpaById(filmStorage.getMpaId(filmId)),
                genreService.getFilmGenres(filmId));
    }

    @Override
    public Film createFilm(Film film) throws ValidationException {
        filmStorage.createFilm(film, mpaService.getMpaById(film.getMpa().getId()),
                film.getGenres());
        if (film.getGenres() != null) {
            genreService.putGenres(film);
        }
        return film;
    }

    @Override
    public Film updateFilm(Film film) throws ValidationException {
        filmStorage.updateFilm(film, mpaService.getMpaById(film.getMpa().getId()),
                film.getGenres());
        film.setMpa(mpaService.getMpaById(film.getMpa().getId()));
        if (film.getGenres() != null) {
            Collection<Genre> sortGenres = film.getGenres().stream()
                    .sorted(Comparator.comparing(Genre::getId))
                    .collect(Collectors.toList());
            film.setGenres(new LinkedHashSet<>(sortGenres));

            for (Genre genre : film.getGenres()) {
                genre.setName(genreService.getGenreById(genre.getId()).getName());
            }
            genreService.putGenres(film);
        }

        return film;
    }
}
