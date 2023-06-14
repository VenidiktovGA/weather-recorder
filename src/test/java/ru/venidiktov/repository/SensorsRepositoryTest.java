package ru.venidiktov.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.venidiktov.util.BaseTest;
import ru.venidiktov.model.Sensors;

@Transactional(propagation = Propagation.NOT_SUPPORTED)
class SensorsRepositoryTest extends BaseTest {

    @AfterEach
    void clear() {
        sensorsRepository.deleteAll();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Cенсор №1", "cенсор №1", "СЕНСОР №1", "СеНсOр №1"})
    void findByNameIgnoreCase_ReturnExistSensor_IfSensorByNameExist() {
        Sensors sensor = Sensors.builder().name("Сенсор №1").build();
        sensorsRepository.saveAndFlush(sensor);
        String incomingSensorName = "Сенсор №1";

        Sensors existSensor = sensorsRepository.findByNameIgnoreCase(incomingSensorName).orElseThrow(() -> new RuntimeException("the sensor is not included, the sensor must be"));

        assertAll(
                () -> assertThat(existSensor).isNotNull(),
                () -> assertThat(existSensor.getName()).isEqualTo(incomingSensorName),
                () -> assertThat(existSensor.getId()).isNotNull(),
                () -> assertThat(existSensor.getRegistrationDate()).isAfterOrEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0)))
        );
    }

    @Test
    void save_throwUniqueSqlException_IfWriteSensorWithExistName() {
        String existNameSensor = "Сенсор с таким именем уже есть";
        sensorsRepository.saveAndFlush(new Sensors(existNameSensor));
        assertThatThrownBy(() -> {
            sensorsRepository.saveAndFlush(new Sensors(existNameSensor));
        }).isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("duplicate key value violates unique constraint \"sensors_name_unq\"");
    }
}