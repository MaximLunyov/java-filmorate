package ru.yandex.practicum.filmorate.service.mpa;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

public interface MpaService {
    public Collection<Mpa> getAllMpa();

    public Mpa getMpaById(Integer id) throws ValidationException;
}
