package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InvalidException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final LocalDate notEarlier = LocalDate.of(1895, 12, 28);
    private int idGenerate = 1;
    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> findAll() {
        log.info("Получен запрос GET films, возвращаем список всех имеющихся фильмах кол-во '{}' ", films.size());
        return films.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        film.setId(idGenerate++);
        films.put(film.getId(), film);
        log.info("Получен запрос POST film, добавлен фильм id '{}' название '{}'  ", film.getId(), film.getName());
        return film;
    }

    @PutMapping
    public Film filmUpdate(@Valid @RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            log.warn("Фильм с таким id '{}' не обнаружен ", film.getId());
            throw new InvalidException("Фильм с таким id не обнаружен");
        }
        films.put(film.getId(), film);
        log.info("Получен запрос PUT film, обновлен фильм id '{}' название '{}'  ", film.getId(), film.getName());
        return film;
    }
}
