package ru.venidiktov.service;

import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.venidiktov.dto.request.SensorRq;
import ru.venidiktov.exception.SensorException;
import ru.venidiktov.model.Sensors;
import ru.venidiktov.util.BaseTest;

@Transactional(propagation = Propagation.NOT_SUPPORTED)
class SensorsServiceTest extends BaseTest {

    @Nested
    class RegistrationSensor {
        @AfterEach
        void clear() {
            sensorsRepository.deleteAll();
        }

        @Test
        void successRegistrationSensor() {
            var request = new SensorRq("Сенсор №210");

            var response = sensorsService.registrationSensor(request);

            var existSensor = sensorsRepository.findAll();
            assertAll(
                    () -> assertThat(existSensor).isNotNull(),
                    () -> assertThat(existSensor.get(0).getName()).isEqualTo(request.getName()),
                    () -> assertThat(existSensor.get(0).getRegistrationDate().toLocalDate()).isEqualTo(LocalDate.now()),
                    () -> assertThat(response.getMessage()).isEqualTo(String.format("Сенсор с именем '%s' успешно зарегистрирован", request.getName()))
            );
        }

        @Test
        void throwExceptionIfSensorWithSameNameExist() {
            String existNameSensor = "Сенсор с таким именем уже есть";
            sensorsRepository.saveAndFlush(new Sensors(existNameSensor));

            assertThatThrownBy(() -> {
                sensorsService.registrationSensor(new SensorRq(existNameSensor));
            }).isInstanceOf(SensorException.class)
                    .hasMessageContaining(String.format("Сенсор с именем '%s' уже есть", existNameSensor));
            Mockito.verify(sensorsValidate).validate(existNameSensor);
        }
    }

    @Nested
    class GetSensorByNameIgnoreCase {
        @AfterEach
        void clear() {
            sensorsRepository.deleteAll();
        }

        @ParameterizedTest
        @ValueSource(strings = {"СЕНСОР №21", "сенсор №21", "СЕНсор №21"})
        void successReturnSensorByName_ifSensorExist(String sensorName) {
            sensorsRepository.saveAndFlush(new Sensors("Сенсор №21"));

            var findingSensor = sensorsService.getSensorByNameIgnoreCase(sensorName);

            assertAll(
                    () -> assertThat(findingSensor).isNotNull(),
                    () -> assertThat(findingSensor.getName()).isEqualToIgnoringCase(sensorName)
            );
        }

        @Test
        void throwException_ifSensorNotExist() {
            assertThatThrownBy(() -> {
                sensorsService.getSensorByNameIgnoreCase("Сенсор №21");
            }).isInstanceOf(SensorException.class)
                    .hasMessageContaining("Сенсор с именем 'Сенсор №21' не найден");
        }
    }
}