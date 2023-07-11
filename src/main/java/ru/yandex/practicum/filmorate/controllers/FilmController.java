package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.dao.film.FilmStorage;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private FilmStorage filmStorage;
    private FilmService filmService;

    @Autowired
    public FilmController(@Qualifier("filmDbStorage") FilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> filmList() throws ValidationException {
        return filmService.findAllFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) throws ValidationException {
        if (id <= 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return filmService.getFilmById(id);
    }

    @ResponseBody
    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        film = filmService.createFilm(film);
        return film;
    }

    @ResponseBody
    @PutMapping
    public Film update(@Valid @RequestBody Film film) throws ValidationException {
        film = filmService.updateFilm(film);
        return film;
    }

    @PutMapping("/{id}/like/{userId}")
    public void likeFilm(@PathVariable int id, @PathVariable int userId) throws ValidationException {
        filmService.likeFilm(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) throws ValidationException {
        if (id < 1 || userId < 1) {
            throw new NoSuchElementException();
        }
        filmService.deleteLike(id, userId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) throws ValidationException {
        filmStorage.delete(id);
    }

    @GetMapping("/popular")
    public List<Film> getPopular(@RequestParam(name = "count", defaultValue = "10") Integer count) throws ValidationException {
        return filmService.getPopular(count);
    }
}