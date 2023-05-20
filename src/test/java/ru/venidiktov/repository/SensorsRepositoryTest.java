package ru.venidiktov.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.venidiktov.BaseTest;
import ru.venidiktov.model.Sensors;

class SensorsRepositoryTest extends BaseTest {

    @BeforeEach
    void init() {
        Sensors sensor = Sensors.builder().name("Сенсор №1").build();
        sensorsRepository.saveAndFlush(sensor);
    }

    @Test
    void findByName_ReturnExistSensor_IfSensorByNameExist() {
        String incomingSensorName = "Сенсор №1";

        Sensors existSensor = sensorsRepository.findByName(incomingSensorName).orElseThrow(() -> new RuntimeException("the sensor is not included, the sensor must be"));

        assertAll(
                () -> assertThat(existSensor).isNotNull(),
                () -> assertThat(existSensor.getName()).isEqualTo(incomingSensorName),
                () -> assertThat(existSensor.getId()).isNotNull(),
                () -> assertThat(existSensor.getRegistrationDate()).isAfterOrEqualTo(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0)))
        );
    }
}