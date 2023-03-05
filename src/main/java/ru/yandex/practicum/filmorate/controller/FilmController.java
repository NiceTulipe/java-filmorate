package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InvalidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @GetMapping
    public Collection<Film> findAllFilms() {
        log.info("Получен запрос GET films, возвращаем список всех имеющихся фильмах ");
        return filmService.findAllFilms();
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос POST film, добавлен фильм id '{}' название '{}'  ", film.getId(), film.getName());
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film filmUpdate(@Valid @RequestBody Film film) {
        log.info("Получен запрос PUT film, обновлен фильм id '{}' название '{}'  ", film.getId(), film.getName());
        return filmService.filmUpdate(film);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Integer id) {
        log.info("Получен запрос GET film id '{}' ", id);
        return filmService.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLikeAtFilm(@PathVariable Integer id,
                              @PathVariable Integer userId) {
        log.info("Получен запрос PUT user id '{}' на добавление оценки фильму с id '{}'  ", userId, id);
        filmService.addLikeFilm(id, userId);
    }

    @DeleteMapping("{id}/like/{userId}")
    public void deleteFriend(@PathVariable Integer id,
                             @PathVariable Integer userId) {
        log.info("Получен запрос DELETE от user id '{}' на удаление оценки у фильма с id '{}'  ", userId, id);
        filmService.deleteLikeFilm(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getRatingFilms(//@PathVariable Integer count,
                                     @RequestParam(defaultValue = "10", required = false) Integer count) {
        log.info("Получен запрос GET на получение топ  '{}' фильмов ", count);
        return filmService.topRatingFilms(count);
    }


}
