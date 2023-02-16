package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.*;
import java.time.LocalDate;


@Data
@NonNull
public class Film {
    private final int lengthDescription = 200;
    @Positive(message = "Id должен быть положительным")
    private Integer id;

    @NotBlank(message = "Название не может быть пустым")
    private String name;

    @NotBlank
    @Size(message = "максимальная длина описания равна " + lengthDescription, max = lengthDescription)
    private String description;

    //  @PastOrPresent(message = "Релиз фильма не может быть в будущем")
    private LocalDate releaseDate;

    @Positive(message = "Продолжительность должна быть больше 0")
    private Integer duration;
}
