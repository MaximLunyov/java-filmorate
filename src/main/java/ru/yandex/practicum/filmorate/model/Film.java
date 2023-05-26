package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {
    private int id;
    @NonNull
    @NotBlank
    private String name;
    @Size(max=200, message = "Описание не должно превышать 200 символов!")
    private String description;
    private LocalDate releaseDate;
    private Duration duration;

    public Film (@NotNull String name, String description, LocalDate releaseDate, long duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = Duration.ofMinutes(duration);
    }
}
