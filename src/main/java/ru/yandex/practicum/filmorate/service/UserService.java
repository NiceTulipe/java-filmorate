package ru.yandex.practicum.filmorate.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    public void addFriend(Integer userId, Integer otherId) {
        if (!userStorage.checkerUserID(userId) && !userStorage.checkerUserID(otherId)) {
            log.info("Пользователь с id '{}' добавил друга с friendId '{}'", userId, otherId);
            userStorage.getUserById(userId).getFriendsList().add(otherId);
            userStorage.getUserById(otherId).getFriendsList().add(userId);
        }
    }

    public void deleteFriend(Integer userId, Integer otherId) {
        if (!userStorage.checkerUserID(userId) && !userStorage.checkerUserID(otherId)) {
            log.info("Пользователь с id '{}' удалил друга с friendId '{}'", userId, otherId);
            userStorage.getUserById(userId).getFriendsList().remove(otherId);
            userStorage.getUserById(otherId).getFriendsList().remove(userId);
        }
    }

    public List<User> crossingFriends(Integer userId, Integer otherId) {
        List<User> listOfCrossingFriends = new ArrayList<>();
        List<Integer> listOfFriends = userStorage.getUserById(userId).getFriendsList()
                .stream()
                .filter(userStorage.getUserById(otherId).getFriendsList()::contains)
                .collect(Collectors.toList());
        for (Integer idCrossingFriends : listOfFriends) {
            listOfCrossingFriends.add(userStorage.getUserById(idCrossingFriends));
        }
        return listOfCrossingFriends;
    }

    public List<User> friendsList(Integer userId) {
        return userStorage.friendsList(userId);
    }

    public Collection<User> findAllUsers() {
        log.info("Вызов списка всех пользователей");
        return userStorage.findAllUsers();
    }

    public User getUserById(Integer userId) {
        log.info("Вызов пользователя с ID'{}' ", userId);
        return userStorage.getUserById(userId);
    }

    public User createUser(@Valid @RequestBody User user) {
        log.info("Создание нового пользователя, присвоен ID'{}' ", user.getId());
        return userStorage.createUser(user);
    }

    public User userUpdate(@Valid @RequestBody User user) {
        log.info("Обновление пользователя с  ID'{}' ", user.getId());
        return userStorage.userUpdate(user);
    }

    public void deleteUser(Integer userId) {
        log.info("Удаление пользователя с ID'{}' ", userId);
        userStorage.deleteUser(userId);
    }

    public void deleteAllUsers() {
        log.info("Удаление всех пользователей ");
        userStorage.deleteAllUsers();
    }

}
