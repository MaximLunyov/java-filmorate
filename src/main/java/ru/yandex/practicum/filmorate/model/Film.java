package ru.yandex.practicum.filmorate.model;
import lombok.Data;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {
    private int id;
    @NonNull
    private String name;
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
