package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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
    public Film getFilmById(int id) {
        if (!filmMap.containsKey(id)) {
            throw new NoSuchElementException("Фильм с указанным id не найден");
        }
        return filmMap.get(id);
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
    public Film likeFilm(int id, int otherId) {
        if (filmMap.containsKey(id)) {
            filmMap.get(id).getLikes().add((long) otherId);
        }
        return filmMap.get(id);
    }

    @Override
    public Film deleteLike(int id, int otherId) {
        if (filmMap.containsKey(id) && filmMap.get(id).getLikes().contains((long) otherId)) {
            filmMap.get(id).getLikes().remove((long) otherId);
        }
        return filmMap.get(id);
    }

    @Override
    public List<Film> getMostPopular(int count) {
        List<Film> sortedFilmsByLikes = new ArrayList<>(filmMap.values());

        sortedFilmsByLikes.sort(Comparator.comparing(Film::getLikesCount));
        Collections.reverse(sortedFilmsByLikes);

        if (count <= sortedFilmsByLikes.size()) {
            sortedFilmsByLikes.subList(count, sortedFilmsByLikes.size()).clear();
        }

        return sortedFilmsByLikes;
    }
}
