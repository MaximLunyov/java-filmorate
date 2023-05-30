package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
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
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Получен запрос на добавление фильма");
        validate(film);
        film.setId(++id);
        filmMap.put(film.getId(), film);

        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Получен запрос на обновление фильма");
        validate(film);
        if (filmMap.containsKey(film.getId())) {
            filmMap.put(film.getId(), film);
        } else {
            throw new ValidationException("Фильм с указанным id не найден");
        }
        return film;
    }

    public void validate(Film film) throws ValidationException {
        if (film.getName().isEmpty() || film.getName().isBlank()) {
            throw new ValidationException("Название не может быть пустым!");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Описание не может превышать 200 символов!");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата выхода фильма указана неверно!");
        }
        if (film.getDuration() < 0) {
            throw new ValidationException("Длительность фильма не может быть отрицательной!");
        }
    }
}
