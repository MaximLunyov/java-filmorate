package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

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

    public User(@NotBlank String login, String name, String email, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    /*public User(@NotBlank(message = "Логин не может быть пустым.")
                @Pattern(regexp = "(?=\\S+$).+", message = "Логин не может содержать пробелы.") String login,
                String name,
                @Email String email,
                @PastOrPresent(message = "День рождения не может быть в будущем.") LocalDate birthday) {

        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }*/
}
