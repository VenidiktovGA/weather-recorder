package ru.venidiktov.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Измерение", description = "Измерение из базы данных")
public class MeasurementRs {

    @Schema(description = "Значение измерения")
    private Double value;

    @Schema(description = "Дождливый ли день")
    private Boolean raining;

    @Schema(description = "Сенсор")
    private MeasurementSensorRs sensor;
}
