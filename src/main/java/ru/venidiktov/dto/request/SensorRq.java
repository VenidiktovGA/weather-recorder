package ru.venidiktov.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Зарегистрировать сенсор", description = "Сенсор на регистрации")
public class SensorRq {

    @NotNull(message = "Поле name обязательно")
    @Size(min = 3, max = 30, message = "Имя сенсора должно быть от 3 до 30 символов")
    @Schema(description = "Имя сенсора", required = true, minLength = 3, maxLength = 30)
    private String name;

    /*Используется при серриализации json в класс когда он находится в другом классе*/
    public SensorRq(String name) {
        this.name = name.trim();
    }

    /*Используется при серриализации прямого json в класс*/
    public void setName(String name) {
        this.name = name.trim();
    }
}
