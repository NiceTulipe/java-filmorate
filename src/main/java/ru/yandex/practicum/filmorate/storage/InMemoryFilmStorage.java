package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.InvalidException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final LocalDate notEarlier = LocalDate.of(1895, 12, 28);
    private int idGenerate = 1;
    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public Integer filmRatingById(Integer filmId) {
        return films.get(filmId).getFilmRating().size();
    }

    @Override
    public Collection<Film> findAllFilms() {
        if (films.isEmpty()) {
            log.info("Список фильмов пуст");
        }
        log.info("Получен запрос GET films, возвращаем список всех имеющихся фильмах кол-во '{}' ", films.size());
        return films.values();
    }

    @Override
    public Film getFilmById(Integer filmId) {
        checkerFilm(filmId);
        log.info("Получен запрос GET film by Id, возвращаем фильм с id  '{}' под названием '{}' ", films.get(filmId), films.get(filmId).getName());
        return films.get(filmId);
    }

    @Override
    public Film createFilm(@Valid @RequestBody Film film) {
        film.setId(idGenerate++);
        films.put(film.getId(), film);
        log.info("Получен запрос POST film, добавлен фильм id '{}' название '{}'  ", film.getId(), film.getName());
        return film;
    }

    @Override
    public Film filmUpdate(@Valid @RequestBody Film film) {
        checkerFilm(film.getId());
        films.put(film.getId(), film);
        log.info("Получен запрос PUT film, обновлен фильм id '{}' название '{}'  ", film.getId(), film.getName());
        return film;
    }

    @Override
    public void deleteFilmById(Integer filmId) {
        checkerFilm(filmId);
        log.info("Получен запрос DELETE film, удален фильм id '{}' название '{}'  ", films.get(filmId), films.get(filmId).getName());
        films.remove(filmId);
    }

    @Override
    public void deleteAllFilms() {
        log.info("Получен запрос на удавление всех фильмов");
        films.clear();
    }

    @Override
    public boolean checkerFilm(Integer filmId) {
        if (!films.containsKey(filmId)) {
            log.warn("Фильм с таким id '{}' не обнаружен ", films.get(filmId));
            throw new FilmNotFoundException("Фильм с таким id не обнаружен");
        }
        return false;
    }

}

