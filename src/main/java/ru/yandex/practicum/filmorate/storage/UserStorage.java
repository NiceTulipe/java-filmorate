package ru.yandex.practicum.filmorate.storage;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

public interface UserStorage {
    Collection<User> findAllUsers();

    User getUserById(Integer userId);

    User createUser(User user);

    User userUpdate(User user);

    void deleteUser(Integer userId);

    void deleteAllUsers();

    List<User> friendsList(Integer userId);

    boolean checkerUserID(Integer id);
}
