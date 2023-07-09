package ru.yandex.practicum.filmorate.service.genre;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.genre.GenreDbStorage;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GenreServiceImpl implements GenreService{
    private GenreDbStorage genreDbStorage;

    @Autowired
    public GenreServiceImpl(GenreDbStorage genreDbStorage) {
        this.genreDbStorage = genreDbStorage;
    }

    public Collection<Genre> getGenres() {
        return genreDbStorage.getGenres().stream()
                .sorted(Comparator.comparing(Genre::getId))
                .collect(Collectors.toList());
    }

    public Genre getGenreById(Integer id) throws ValidationException {
        return genreDbStorage.getGenreById(id);
    }

    public void putGenres(Film film) {
        genreDbStorage.delete(film);
        genreDbStorage.add(film);
    }

    public Set<Genre> getFilmGenres(int filmId) {
        Set<Genre> genres = new HashSet<>();
        if (!genreDbStorage.getFilmGenres(filmId).isEmpty()) {
            return new HashSet<>(genreDbStorage.getFilmGenres(filmId));
        } else {
            return genres;
        }
    }
}
