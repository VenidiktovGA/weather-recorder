package ru.venidiktov.dto;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.venidiktov.model.Sensors;
import ru.venidiktov.service.SensorsService;

@ExtendWith(MockitoExtension.class)
class MeasurementsRqTest {

    @Mock
    private SensorsService sensorsService;

    @Test
    void toMeasurements_success() {
        doReturn(new Sensors("Сенсор №1")).when(sensorsService).getSensorByNameIgnoreCase("Сенсор №1");
        var request = new MeasurementsRq(50.5, false, new RegistrationSensorRq("Сенсор №1"));

        var measurements = request.toMeasurements(sensorsService);

        assertAll(
                () -> assertThat(measurements.getValue()).isEqualTo(request.getValue()),
                () -> assertThat(measurements.getRaining()).isEqualTo(request.getRaining()),
                () -> assertThat(measurements.getSensorName()).isEqualTo(request.getSensor().getName()),
                () -> assertThat(measurements.getSensor().getName()).isEqualTo(request.getSensor().getName())
        );
    }
}