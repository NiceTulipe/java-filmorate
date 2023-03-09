package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


class UserControllerTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void createValidatorFactory() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void closeValidatorFactory() {
        validatorFactory.close();
    }

    User Charmander = User.builder()
            .name("Charmander")
            .login("CharChar")
            .birthday(LocalDate.of(1995,04,22))
            .email("fire_pokemon@nintendo.com")
            .id(4).build();
    User Bulbasaur = User.builder()
            .name("Bulbasaur")
            .login("BulbaBulba")
            .birthday(LocalDate.of(1994,06,12))
            .email("grass_pokemon@nintendo.com")
            .id(1).build();
    User Squirtle = User.builder()
            .name("Squirtle")
            .login("SquirleSquirle")
            .birthday(LocalDate.of(1992,03,14))
            .email("water_pokemon@nintendo.com")
            .id(7).build();

    @Test
    public void createUserTrue() {
        User Charmander = User.builder()
                .name("Charmander")
                .login("CharChar")
                .birthday(LocalDate.of(1995,04,22))
                .email("fire_pokemon@nintendo.com")
                .id(4).build();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(Charmander);
        assertEquals(0, constraintViolations.size(), "юзер корректен, ошибка в логике проверки");
    }

    @Test
    public void createUser_ErrorNameNumber() {
        User Charmander = User.builder()
                .name("Charmander1")
                .login("CharChar")
                .birthday(LocalDate.of(1995,04,22))
                .email("fire_pokemon@nintendo.com")
                .id(4).build();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(Charmander);
        assertEquals(1, constraintViolations.size(), "ошибка в имени");
    }

    @Test
    public void createUser_ErrorNamePercent() {
        User Charmander = User.builder()
                .name("Charmander%")
                .login("CharChar")
                .birthday(LocalDate.of(1995,04,22))
                .email("fire_pokemon@nintendo.com")
                .id(4).build();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(Charmander);
        assertEquals(1, constraintViolations.size(), "ошибка в имени");
    }

    @Test
    public void createUser_ErrorLoginCirillica() {
        User Charmander = User.builder()
                .name("Charmander")
                .login("CharChar_ЩЩЩ")
                .birthday(LocalDate.of(1995,04,22))
                .email("fire_pokemon@nintendo.com")
                .id(4).build();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(Charmander);
        assertEquals(1, constraintViolations.size(), "ошибка в логине используется кириллица");
    }

    @Test
    public void createUser_TrueLogin() {
        User Charmander = User.builder()
                .name("Charmander")
                .login("_233_CharChar_")
                .birthday(LocalDate.of(1995,04,22))
                .email("fire_pokemon@nintendo.com")
                .id(4).build();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(Charmander);
        assertEquals(0, constraintViolations.size(), "создается юзер ");
    }

    @Test
    public void createUser_ErrorAtBirthday_Future() {
        User Charmander = User.builder()
                .name("Charmander")
                .login("CharChar")
                .birthday(LocalDate.of(2025,04,22))
                .email("fire_pokemon@nintendo.com")
                .id(4).build();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(Charmander);
        assertEquals(1, constraintViolations.size(), "ошибка в дате рождения(в будущем)");
    }

    @Test
    public void createUser_ErrorAtEmail() {
        User Charmander = User.builder()
                .name("Charmander")
                .login("CharChar")
                .birthday(LocalDate.of(1995,04,22))
                .email("ввыв@nintendo.com")
                .id(4).build();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(Charmander);
        assertEquals(1, constraintViolations.size(), "ошибка в email присутствует кириллица ");
    }

    @Test
    public void createUser_ErrorEmptyEmail() {
        User Charmander = User.builder()
                .name("Charmander")
                .login("CharChar")
                .birthday(LocalDate.of(1995,04,22))
                .email("")
                .id(4).build();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(Charmander);
        assertEquals(2, constraintViolations.size(), "ошибка в email он пуст ");
    }




}