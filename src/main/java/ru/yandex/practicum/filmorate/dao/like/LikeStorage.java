package ru.yandex.practicum.filmorate.dao.like;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikeStorage {

        public void likeFilm(int filmId, int userId);

        public void deleteLike(int filmId, int userId);

        public List<Film> getPopular(Integer count);

        public List<Long> getLikes(int filmId);
}
