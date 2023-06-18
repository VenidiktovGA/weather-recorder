package ru.venidiktov.model;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class MeasurementsTest {
    @Test
    void successConvertMeasurementsToMeasurementRs() {
        var sensor = new Sensors("Вектор М");
        var measurements = Measurements.builder()
                .value(22.22)
                .raining(true)
                .created(LocalDateTime.now())
                .sensorName(sensor.getName())
                .sensor(sensor)
                .build();

        var measurementRs = measurements.toMeasurementRs();

        assertAll(
                () -> assertThat(measurementRs.getValue()).isEqualTo(measurements.getValue()),
                () -> assertThat(measurementRs.getRaining()).isEqualTo(measurements.getRaining()),
                () -> assertThat(measurementRs.getSensor().getName()).isEqualTo(measurements.getSensor().getName())
        );
    }
}