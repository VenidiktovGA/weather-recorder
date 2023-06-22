package ru.venidiktov.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Сенсор", description = "Сенсор из базы данных")
public class MeasurementSensorRs {
    @Schema(description = "Имя сенсора")
    private String name;
}