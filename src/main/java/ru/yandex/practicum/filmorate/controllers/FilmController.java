package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class FilmController {
    private Map<Integer, Film> filmMap = new HashMap<>();
    protected int id = 0;

    @GetMapping("/film-get")
    public List<Film> filmMap() {
        return new ArrayList<>(filmMap.values());
    }

    @PostMapping("/film-add")
    public Film create(@RequestBody Film film) throws ValidationException {
        log.info("Получен запрос на добавление фильма");
        Film film1 = validation(film);
        if (film1 != null) {
            film.setId(++id);
            filmMap.put(film.getId(), film);
        }
        return film;
    }

    @PatchMapping("film-update")
    public Film update(@RequestBody Film film) throws ValidationException {
        log.info("Получен запрос на обновление фильма");
        if (filmMap.containsKey(film.getId())) {
            Film film1 = validation(film);
            if (film1 != null) {
                filmMap.put(film.getId(), film);
            }
        }
        return film;
    }

    public static Film validation(Film film) throws ValidationException {
        Film film1 = film;
        try {
            if (film.getName().isEmpty() || film.getName().isBlank()) {
                film1 = null;
                throw new ValidationException("Название не может быть пустым!");
            }
            if (film.getDescription().length() > 200) {
                film1 = null;
                throw new ValidationException("Описание не может превышать 200 символов!");
            }
            if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
                film1 = null;
                throw new ValidationException("Дата выхода фильма указана неверно!");
            }
            if (film.getDuration().toSeconds() < 0) {
                film1 = null;
                throw new ValidationException("Длительность фильма не может быть отрицательной!");
            }
        } catch (ValidationException e) {
            log.info(e.getMessage());
        }

        return film1;
    }
}
