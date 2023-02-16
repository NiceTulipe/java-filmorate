package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InvalidException;
import ru.yandex.practicum.filmorate.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/users")

public class UserController {

    private int idGenerate = 1;

    private final Map<Integer, User> users = new HashMap<>();
    private final List<String> emails = new ArrayList<>();

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        if (emails.contains(user.getEmail())) {
            log.warn("Пользователь с email '{}' уже существует", user.getEmail());
            throw new AlreadyExistException("Пользователь с таким email уже существует");
        }
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        user.setId(idGenerate++);
        users.put(user.getId(), user);
        emails.add(user.getEmail());
        log.info("Получен запрос POST user, добавлен пользователь id '{}' email '{}'  ", user.getId(), user.getEmail());
        return user;
    }


    @PutMapping

    public User userUpdate(@Valid @RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            log.warn("Пользователь с таким id '{}' не обнаружен ", user.getId());
            throw new InvalidException("Пользователь с таким id не обнаружен");
        }
        if (emails.contains(user.getEmail())) {
            log.debug("Получен запрос к эндпоинту: 'PUT /users', такой эмейл уже существует '{}'",
                    user.getEmail());
            throw new AlreadyExistException("Пользователь с таким email существует");
        }

        users.put(user.getId(), user);
        log.info("Получен запрос PUT user, обновлен user id '{}' Email '{}'  ", user.getId(), user.getEmail());
        return user;
    }
}

