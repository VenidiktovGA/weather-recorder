package ru.venidiktov.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorsDto {

    @Size(min = 3, max = 30, message = "Имя сенсора должно быть от 3 до 30 символов")
    private String name;
}
