package ru.yandex.practicum.filmorate.storage;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.AlreadyExistException;
import ru.yandex.practicum.filmorate.exception.InvalidException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    Map<Integer, User> users = new HashMap<>();
    List<String> emails = new ArrayList<>();
    private int idGenerate = 1;

    @Override
    public Collection<User> findAllUsers() {
        return users.values();
    }

    @Override
    public User getUserById(Integer userId) {
        checkerUserID(userId);
        log.info("Получен запрос GET user by Id, возвращаем пользователя с id  '{}' под именем '{}' ", users.get(userId), users.get(userId).getName());
        return users.get(userId);
    }

    @Override
    public User createUser(@Valid @RequestBody User user) {
        checkerUserEmail(user.getEmail());
        if (user.getName() == null || user.getName().isBlank() || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        user.setId(idGenerate++);
        users.put(user.getId(), user);
        emails.add(user.getEmail());
        log.info("Получен запрос POST user, добавлен пользователь id '{}' email '{}'  ", user.getId(), user.getEmail());
        return user;
    }

    @Override
    public User userUpdate(@Valid @RequestBody User user) {
        checkerUserID(user.getId());
        if (emails.contains(user.getEmail())) {
            log.debug("Получен запрос к эндпоинту: 'PUT /users', такой эмейл уже существует '{}'",
                    user.getEmail());
            throw new AlreadyExistException("Пользователь с таким email существует");
        }
        users.put(user.getId(), user);
        log.info("Получен запрос PUT user, обновлен user id '{}' Email '{}'  ", user.getId(), user.getEmail());
        return user;
    }

    @Override
    public void deleteUser(Integer userId) {
        checkerUserID(userId);
        log.info("Получен запрос DELETE user, удален пользователь id '{}' имя '{}'  ", users.get(userId), users.get(userId).getName());
        users.remove(userId);
    }

    @Override
    public void deleteAllUsers() {
        log.info("Получен запрос на удавление всех пользователей");
        users.clear();
    }

    @Override
    public List<User> friendsList(Integer userId) {
        List<User> friendsList = new ArrayList<>();
        for (int idFriends : users.get(userId).getFriendsList()) {
            friendsList.add(users.get(idFriends));
        }
        return friendsList;
    }

    @Override
    public boolean checkerUserID(Integer userId) {
        if (!users.containsKey(userId)) {
            log.warn("Пользователь с таким id '{}' не обнаружен ", users.get(userId));
            throw new UserNotFoundException("Пользователь с таким id не обнаружен");
        }
        return false;
    }

    private void checkerUserEmail(String email) {
        if (emails.contains(email)) {
            log.debug("Получен запрос к эндпоинту: 'PUT /users', такой эмейл уже существует '{}'",
                    email);
            throw new AlreadyExistException("Пользователь с таким email существует");
        }
    }

}

