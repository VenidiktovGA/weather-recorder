package ru.venidiktov.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Ответ о регистрации сенсора")
public class RegistrationRs {
    @Schema(description = "Сообщение описывающее результат попытки регистрации")
    private String message;
}
