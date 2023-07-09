package ru.yandex.practicum.filmorate.dao.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private Map<Integer, Film> filmMap = new HashMap<>();
    protected int id = 0;


    @Override
    public List<Film> findAllFilms() {
        return new ArrayList<>(filmMap.values());
    }

    @Override
    public Film createFilm(Film film) {
        film.setId(++id);
        filmMap.put(film.getId(), film);

        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (filmMap.containsKey(film.getId())) {
            filmMap.put(film.getId(), film);
        } else {
            throw new NoSuchElementException("Фильм с указанным id не найден");
        }
        return film;
    }

    @Override
    public Film getFilmById(int id) {
        if (!filmMap.containsKey(id)) {
            throw new NoSuchElementException("Фильм с указанным id не найден");
        }
        return filmMap.get(id);
    }

    @Override
    public Film delete(int filmId) throws ValidationException {
        return null;
    }
}