package ru.yandex.practicum.filmorate.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.InvalidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public void addLikeFilm(Integer filmId, Integer userId) {
        if (!userStorage.checkerUserID(userId) && !filmStorage.checkerFilm(filmId)) {
            log.info("Пользователь с id '{}' добавил оценку фильму с id '{}' и названием '{}'", userId, filmId, filmStorage.getFilmById(filmId).getName());
            filmStorage.getFilmById(filmId).getFilmRating().add(userId);
        }
    }

    public void deleteLikeFilm(Integer filmId, Integer userId) {
        if (!userStorage.checkerUserID(userId) && !filmStorage.checkerFilm(filmId)) {
            log.info("Пользователь с id '{}' удалил оценку фильму с id '{}' и названием '{}'", userId, filmId, filmStorage.getFilmById(filmId).getName());
            filmStorage.getFilmById(filmId).getFilmRating().remove(userId);
        }
    }

    public Integer filmRatingById(Integer filmId) {
        return filmStorage.filmRatingById(filmId);
    }

    public List<Film> topRatingFilms(Integer topSize) {


        HashMap<Integer, Integer> ratingMap = new HashMap<Integer, Integer>();
        for (Film film : filmStorage.findAllFilms()) {
            ratingMap.put(film.getId(), film.getFilmRating().size());
        }

        List<Integer> list = new ArrayList<>();
        list = ratingMap.keySet().stream()
                .sorted(Comparator.reverseOrder())
                .limit(topSize)
                .collect(Collectors.toList());
        List<Film> topFilmsChart = new ArrayList<>();
        for (Integer id : list) {
            topFilmsChart.add(filmStorage.getFilmById(id));
        }
        return topFilmsChart;
    }

    public Collection<Film> findAllFilms() {
        log.info("Вызов списка всех фильмов");
        return filmStorage.findAllFilms();
    }

    public Film getFilmById(Integer filmId) {
        log.info("Запрос на вывод фильма с id{}' ", filmId);
        return filmStorage.getFilmById(filmId);
    }

    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("Добавление нового фильма id '{}' название '{}'  ", film.getId(), film.getName());
        return filmStorage.createFilm(film);
    }


    public Film filmUpdate(@Valid @RequestBody Film film) {
        log.info("Jбновлен фильм id '{}' название '{}'  ", film.getId(), film.getName());
        return filmStorage.filmUpdate(film);
    }

    public void deleteFilmById(Integer filmId) {
        log.info("Удален фильм id '{}' название '{}'  ", filmId, filmStorage.getFilmById(filmId).getName());
        filmStorage.deleteFilmById(filmId);
    }

    public void deleteAllFilms() {
        log.info("Удавление всех фильмов");
        filmStorage.deleteAllFilms();
    }

}
