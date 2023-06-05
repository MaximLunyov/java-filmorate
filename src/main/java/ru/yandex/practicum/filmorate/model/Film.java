package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.NotNull;
import ru.yandex.practicum.filmorate.model.validators.FilmRelease;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class Film {
    private int id;
    @NonNull
    @NotBlank
    private String name;
    @Length(max = 200, message = "Описание не должно превышать 200 символов!")
    private String description;
    @PastOrPresent(message = "Дата релиза не может быть в будущем.")
    @FilmRelease
    private LocalDate releaseDate;
    @Min(value = 0, message = "Продолжительность фильма не может быть отрицательной.")
    private int duration;

    public Film(@NotNull String name, String description, LocalDate releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
