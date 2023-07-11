package ru.yandex.practicum.filmorate.dao.mpa;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface MpaStorage {
    public List<Mpa> getAllMpa();

    public Mpa getMpaById(Integer mpaId) throws ValidationException;
}
