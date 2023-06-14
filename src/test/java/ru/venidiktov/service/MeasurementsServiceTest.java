package ru.venidiktov.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.venidiktov.dto.MeasurementsRq;
import ru.venidiktov.dto.RegistrationSensorRq;
import ru.venidiktov.exception.SensorException;
import ru.venidiktov.model.Sensors;
import ru.venidiktov.util.BaseTest;

@Transactional(propagation = Propagation.NOT_SUPPORTED)
class MeasurementsServiceTest extends BaseTest {

    @Nested
    class AddMeasurements {
        @AfterEach
        void clear() {
            measurementsRepository.deleteAll();
            sensorsRepository.deleteAll();
        }

        @Test
        void successAddMeasurements() {
            var sensorName = "Сенсор №23";
            sensorsRepository.saveAndFlush(new Sensors(sensorName));
            var request = new MeasurementsRq(50.5, false, new RegistrationSensorRq(sensorName));

            measurementsService.addMeasurements(request);

            var measurement = measurementsRepository.findAll().get(0);
            Mockito.verify(measurementsRepository).save(any());
            assertAll(
                    () -> assertThat(measurement).isNotNull(),
                    () -> assertThat(measurement.getSensor().getName()).isEqualTo(sensorName),
                    () -> assertThat(measurement.getRaining()).isFalse(),
                    () -> assertThat(measurement.getValue()).isEqualTo(request.getValue())
            );
        }

        @Test
        void throwException_ifSensorByNameNotExist() {
            var request = new MeasurementsRq(50.5, false, new RegistrationSensorRq("Сенсор №21"));
            assertThatThrownBy(() -> {
                measurementsService.addMeasurements(request);
            }).isInstanceOf(SensorException.class)
                    .hasMessageContaining("Сенсор с именем 'Сенсор №21' не найден");
            Mockito.verify(measurementsRepository, times(0)).save(any());
        }
    }
}