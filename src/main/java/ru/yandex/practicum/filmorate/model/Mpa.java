package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor

public class Mpa {
    @NotNull
    private Integer id;
    @NotBlank
    private String name;
}