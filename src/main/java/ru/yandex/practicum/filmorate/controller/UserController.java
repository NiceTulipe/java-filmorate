package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public Collection<User> findAllUsers() {
        log.info("Получен запрос GET / users");
        return userService.findAllUsers();
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Получен запрос POST user, добавлен пользователь id '{}' email '{}'  ", user.getId(), user.getEmail());
        return userService.createUser(user);
    }

    @PutMapping
    public User userUpdate(@Valid @RequestBody User user) {
        log.info("Получен запрос PUT user, обновлен user id '{}' Email '{}'  ", user.getId(), user.getEmail());
        return userService.userUpdate(user);
    }

    @DeleteMapping
    public void deleteUser(@PathVariable Integer userId) {
        log.info("Получен запрос DELETE user id '{}' ", userId);
        userService.deleteUser(userId);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        log.info("Получен запрос GET user id '{}' ", id);
        return userService.getUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Integer id,
                          @PathVariable Integer friendId) {
        log.info("Получен запрос PUT user id '{}' на добавление друга с id '{}'  ", id, friendId);
        userService.addFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getUserFriendsList(@PathVariable Integer id) {
        log.info("Получен запрос GET user id '{}' на получение списка друзей  ", id);
        return userService.friendsList(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCrossingFriendsList(@PathVariable Integer id,
                                             @PathVariable Integer otherId) {
        log.info("Получен запрос GEt user id '{}' на вывод общийх друзей с пользовтелем id '{}'  ", id, otherId);
        return userService.crossingFriends(id, otherId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Integer id,
                             @PathVariable Integer friendId) {
        log.info("Получен запрос DELETE user id '{}' на удаление друга с id '{}'  ", id, friendId);
        userService.deleteFriend(id, friendId);
    }

}

