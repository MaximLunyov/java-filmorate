package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

@Data
public class User {
    private int id;
    @Email
    private String email;
    @NotBlank(message = "Логин не может быть пустым.")
    @Pattern(regexp = "(?=\\S+$).+", message = "Логин не может содержать пробелы.")
    private String login;
    private String name;
    @PastOrPresent(message = "День рождения не может быть в будущем.")
    private LocalDate birthday;
    private Set<Long> friends = new TreeSet<>();

    public User(@NotBlank String login, String name, String email, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
