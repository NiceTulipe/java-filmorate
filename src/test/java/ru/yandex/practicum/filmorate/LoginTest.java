package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.controller.FilmController;

import static org.assertj.core.api.Assertions.assertThat;


@AutoConfigureMockMvc
@SpringBootTest
    public class LoginTest {

    @Autowired
    private MockMvc mockMvc;

        @Autowired
        private FilmController FilmController;

        @Test
        public void contextLoads() throws Exception {
            assertThat(FilmController).isNotNull();
        }

    }

