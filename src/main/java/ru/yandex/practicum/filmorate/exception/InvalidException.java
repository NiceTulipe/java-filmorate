package ru.yandex.practicum.filmorate.exception;

public class InvalidException extends RuntimeException {
    public InvalidException(String s) {
        super(s);
    }
}