package ru.yandex.practicum.filmorate.storage;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;

public interface FilmStorage {
    Integer filmRatingById(Integer filmId);

    Collection<Film> findAllFilms();

    Film getFilmById(Integer filmId);

    Film createFilm(Film film);

    Film filmUpdate(Film film);

    void deleteFilmById(Integer filmId);

    void deleteAllFilms();

    boolean checkerFilm(Integer filmId);
}
