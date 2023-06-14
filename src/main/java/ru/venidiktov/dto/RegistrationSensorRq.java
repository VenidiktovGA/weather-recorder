package ru.venidiktov.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(name = "Запрос", description = "JSON запроса приходящий к нам с клиента")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationSensorRq {

    @NotNull(message = "Поле name обязательно")
    @Size(min = 3, max = 30, message = "Имя сенсора должно быть от 3 до 30 символов")
    private String name;

    /*Используется при серриализации json в класс когда он находится в другом классе*/
    public RegistrationSensorRq(String name) {
        this.name = name.trim();
    }

    /*Используется при серриализации прямого json в класс*/
    public void setName(String name) {
        this.name = name.trim();
    }
}
