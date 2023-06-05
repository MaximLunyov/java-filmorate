package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("films")
public class FilmController {
    private Map<Integer, Film> filmMap = new HashMap<>();
    protected int id = 0;

    @GetMapping
    public List<Film> filmList() {
        return new ArrayList<>(filmMap.values());
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Получен запрос на добавление фильма");
        film.setId(++id);
        filmMap.put(film.getId(), film);

        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Получен запрос на обновление фильма");
        if (filmMap.containsKey(film.getId())) {
            filmMap.put(film.getId(), film);
        } else {
            throw new ValidationException("Фильм с указанным id не найден");
        }
        return film;
    }
}
