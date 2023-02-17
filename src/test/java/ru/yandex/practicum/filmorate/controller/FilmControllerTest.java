package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


class FilmControllerTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void createValidatorFactory() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void closeValidator() {
        validatorFactory.close();
    }


    @Test
    void postFilm_errorMustBeZero() {
        Film film = Film.builder()
                .id(1)
                .name("Покемон фильм первый: Мьюту против Мью")
                .description("Создан первый искусственный покемон")
                .releaseDate(LocalDate.of(1998, 12, 22))
                .duration(90)
                .build();
        Set<ConstraintViolation<Film>> constraintViolations = validator.validate(film);
        assertEquals(0, constraintViolations.size(), "фильм корректен, ошибка в логике теста");
    }

    @Test
    void postFilm_mustCatchErrorAtDescription() {
        Film film = Film.builder()
                .id(1)
                .name("Покемон фильм первый: Мьюту против Мью")
                .description("Создан первый искусственный покемон" + "Создан первый искусственный покемон" + "Создан первый искусственный покемон"
                        + "Создан первый искусственный покемон" + "Создан первый искусственный покемон" + "Создан первый искусственный покемон"
                        + "Создан первый искусственный покемон" + "Создан первый искусственный покемон" + "Создан первый искусственный покемон"
                        + "Создан первый искусственный покемон" + "Создан первый искусственный покемон" + "Создан первый искусственный покемон")
                .releaseDate(LocalDate.of(1998, 12, 22))
                .duration(90)
                .build();
        Set<ConstraintViolation<Film>> constraintViolations = validator.validate(film);
        assertEquals(1, constraintViolations.size(), "получена ошибка по длине описания");
    }

    @Test
    void postFilm_mustCatchErrorEmptyName() {
        Film film = Film.builder()
                .id(1)
                .name("")
                .description("Создан первый искусственный покемон")
                .releaseDate(LocalDate.of(1998, 12, 22))
                .duration(90)
                .build();
        Set<ConstraintViolation<Film>> constraintViolations = validator.validate(film);
        assertEquals(1, constraintViolations.size(), "получена ошибка название пустое");
    }

    @Test
    void postFilm_mustCatchErrorDurationNegative() {
        Film film = Film.builder()
                .id(1)
                .name("Покемон фильм первый: Мьюту против Мью")
                .description("Создан первый искусственный покемон")
                .releaseDate(LocalDate.of(1998, 12, 22))
                .duration(-889)
                .build();
        Set<ConstraintViolation<Film>> constraintViolations = validator.validate(film);
        assertEquals(1, constraintViolations.size(), "получена ошибка продолжительность отрицательная");
    }

    @Test
    void postFilm_mustCatchErrorDescriptionEmpty() {
        Film film = Film.builder()
                .id(1)
                .name("Покемон фильм первый: Мьюту против Мью")
                .description("")
                .releaseDate(LocalDate.of(1998, 12, 22))
                .duration(90)
                .build();
        Set<ConstraintViolation<Film>> constraintViolations = validator.validate(film);
        assertEquals(1, constraintViolations.size(), "получена ошибка описание отсутствует");
    }

}