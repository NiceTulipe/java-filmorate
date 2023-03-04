package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NonNull
@Builder
public class User {

    private final Set<Integer> friendsList = new HashSet<>();

    @Positive(message = "Id должен быть положительным")
    private Integer id;

    @NotBlank
    @Email(message = "введен некорректный email")
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
            message = "Поле email не должно содержать спец символов")
    private String email;

    @NotBlank
    @Pattern(regexp = "[\\w\\_\\-]*", message = "Поле login не должно содержать спец символов")
    private String login;

    @Builder.Default
    @Pattern(regexp = "[a-zA-Zа-яА-Я\\s]*", message = "Поле name не должно содержать спец символов")
    private String name = "";

    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;
}
