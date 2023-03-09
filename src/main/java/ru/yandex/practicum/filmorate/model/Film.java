package ru.yandex.practicum.filmorate.model;

import jdk.jfr.DataAmount;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.validation.DateForFilms;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
@Builder
@NonNull
public class Film {

    private final Set<Integer> filmRating = new HashSet<>();
    private final int lengthDescription = 200;

    @Positive(message = "Id должен быть положительным")
    private Integer id;

    @NotBlank(message = "Название не может быть пустым")
    private String name;

    @NotBlank(message = "Описание не может быть пустым")
    @Size(message = "максимальная длина описания равна " + lengthDescription, max = lengthDescription)
    private String description;

    @DateForFilms
    private LocalDate releaseDate;

    @Positive(message = "Продолжительность должна быть больше 0")
    private Integer duration;
}
