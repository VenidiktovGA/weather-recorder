package ru.venidiktov.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Запрос", description = "JSON запроса приходящий к нам с клиента")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationSensorRq {

    @NotNull
    @Size(min = 3, max = 30, message = "Имя сенсора должно быть от 3 до 30 символов")
    private String name;

    public void setName(String name) {
        this.name = name.trim();
    }
}
