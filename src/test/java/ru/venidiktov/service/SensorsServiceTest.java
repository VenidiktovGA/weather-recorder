package ru.venidiktov.service;

import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.venidiktov.BaseTest;
import ru.venidiktov.dto.RegistrationSensorRq;
import ru.venidiktov.exception.SensorException;
import ru.venidiktov.model.Sensors;

@Transactional(propagation = Propagation.NOT_SUPPORTED)
class SensorsServiceTest extends BaseTest {

    @AfterEach
    void clear() {
        sensorsRepository.deleteAll();
    }

    @Test
    void successRegistrationSensor() {
        var request = new RegistrationSensorRq("Сенсор №210");

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
            sensorsService.registrationSensor(new RegistrationSensorRq(existNameSensor));
        }).isInstanceOf(SensorException.class)
                .hasMessageContaining(String.format("Сенсор с именем '%s' уже есть", existNameSensor));
        Mockito.verify(sensorsValidate).validate(existNameSensor);
    }
}