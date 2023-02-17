package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.*;
import java.time.LocalDate;


//@Data
//@Builder
//@NonNull
//public class User {
//    @Positive
//    private Integer id;
//    @Email(message = "Поле email не соответствует формату userEmail@email.com")
//    private final String email;
//    @Pattern(message = "Поле login должно содержать только A-Z и 1-9",
//            Pattern ("[A-Za-z0-9]"))
//    private final String login;
//
//    @Builder.Default
//    private String name = "";
//    @PastOrPresent(message = "Поле birthday не корректно")
//    private final LocalDate birthday;
//}


@Data
@NonNull
@Builder
public class User {
    @Positive(message = "Id должен быть положительным")
    private Integer id;

    @NotBlank
    @Email(message = "введен некорректный email")
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Поле email не должно содержать спец символов")
    private String email;

    @NotBlank
    @Pattern(regexp = "[\\w\\_\\-]*", message = "Поле login не должно содержать спец символов")
    private String login;


    @Builder.Default
    @Pattern(regexp = "[a-zA-Zа-яА-Я\\s]*", message = "Поле name не должно содержать спец символов")
    private String name = " ";

    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;


}
