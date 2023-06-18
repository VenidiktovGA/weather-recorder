package ru.venidiktov.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.venidiktov.model.Measurements;
import ru.venidiktov.model.Sensors;
import ru.venidiktov.service.SensorsService;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeasurementRq {

    @NotNull(message = "Поле value обязательно")
    @Min(value = -100, message = "Минимальное значение показателя сенсора -100")
    @Max(value = 100, message = "Максимальное значение показателя сенсора 100")
    private Double value;

    @NotNull(message = "Поле raining обязательно")
    private Boolean raining;

    @Valid
    @NotNull(message = "Поле sensor обязательно")
    private SensorRq sensor;

    public Measurements toMeasurements(SensorsService sensorsService) {
        Sensors existSensor = sensorsService.getSensorByNameIgnoreCase(this.sensor.getName());
        return Measurements.builder()
                .value(value)
                .raining(raining)
                .sensorName(existSensor.getName())
                .sensor(existSensor)
                .build();
    }
}
