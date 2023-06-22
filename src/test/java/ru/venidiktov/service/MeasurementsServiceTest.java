package ru.venidiktov.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.venidiktov.dto.request.MeasurementRq;
import ru.venidiktov.dto.request.SensorRq;
import ru.venidiktov.exception.SensorException;
import ru.venidiktov.model.Measurements;
import ru.venidiktov.model.Sensors;
import ru.venidiktov.util.BaseTest;

@Transactional(propagation = Propagation.NOT_SUPPORTED)
class MeasurementsServiceTest extends BaseTest {
    @AfterEach
    void clear() {
        measurementsRepository.deleteAll();
        sensorsRepository.deleteAll();
    }

    @Nested
    class AddMeasurements {
        @Test
        void successAddMeasurements() {
            var sensorName = "Сенсор №23";
            sensorsRepository.saveAndFlush(new Sensors(sensorName));
            var request = new MeasurementRq(50.5, false, new SensorRq(sensorName));

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
            var request = new MeasurementRq(50.5, false, new SensorRq("Сенсор №21"));
            assertThatThrownBy(() -> {
                measurementsService.addMeasurements(request);
            }).isInstanceOf(SensorException.class)
                    .hasMessageContaining("Сенсор с именем 'Сенсор №21' не найден");
            Mockito.verify(measurementsRepository, times(0)).save(any());
        }
    }

    @Nested
    class GetAllMeasurements {
        @Test
        void successGetAllMeasurements_ifMeasurementsExist() {
            var vectorM = new Sensors("Вектор М");
            var alphaV = new Sensors("Альфа В");
            sensorsRepository.saveAllAndFlush(Arrays.asList(vectorM, alphaV));
            var expectedMeasurements = Arrays.asList(
                    new Measurements(23.0, true, LocalDateTime.now().plusMinutes(1), alphaV.getName(), alphaV),
                    new Measurements(22.2, true, LocalDateTime.now(), vectorM.getName(), vectorM)
            );
            measurementsRepository.saveAllAndFlush(expectedMeasurements);

            var allMeasurements = measurementsService.getAllMeasurements();

            assertAll(
                    () -> assertThat(allMeasurements.stream().map(m -> m.getSensor().getName()))
                            .containsExactlyInAnyOrderElementsOf(expectedMeasurements.stream().map(Measurements::toMeasurementRs).map(m -> m.getSensor().getName()).toList()),
                    () -> assertThat(allMeasurements.get(0).getSensor().getName()).isEqualTo(expectedMeasurements.get(0).getSensor().getName()),
                    () -> assertThat(allMeasurements.get(1).getSensor().getName()).isEqualTo(expectedMeasurements.get(1).getSensor().getName()),
                    () -> assertThat(allMeasurements.get(0).getValue()).isEqualTo(expectedMeasurements.get(0).getValue()),
                    () -> assertThat(allMeasurements.get(1).getValue()).isEqualTo(expectedMeasurements.get(1).getValue()),
                    () -> assertThat(allMeasurements.get(0).getRaining()).isEqualTo(expectedMeasurements.get(0).getRaining()),
                    () -> assertThat(allMeasurements.get(1).getRaining()).isEqualTo(expectedMeasurements.get(1).getRaining())
            );
        }

        @Test
        void returnEmptyList_ifMeasurementsNotExist() {
            var existMeasurements = measurementsService.getAllMeasurements();

            assertThat(existMeasurements).isEmpty();
        }
    }

    @Nested
    class GetCountRainingDays {
        @ParameterizedTest
        @MethodSource("getArgumentsRainingDays")
        void getCountRainingDays(int count, int expectedCountRainingDays) {
            var vectorM = new Sensors("Вектор М");
            sensorsRepository.saveAndFlush(vectorM);
            var measurements = new ArrayList<Measurements>();
            for(int i = 0; i < count; i++) {
                measurements.add(new Measurements(22.2, true, LocalDateTime.now(), vectorM.getName(), vectorM));
            }
            measurementsRepository.saveAllAndFlush(measurements);
            measurementsRepository.saveAndFlush(new Measurements(22.2, false, LocalDateTime.now(), vectorM.getName(), vectorM));

            var rainingDays = measurementsService.getCountRainingDays();

            assertThat(rainingDays).isEqualTo(expectedCountRainingDays);
        }

        static Stream<Arguments> getArgumentsRainingDays() {
            return Stream.of(
                    Arguments.of(0, 0),
                    Arguments.of(1, 1),
                    Arguments.of(5, 5)
            );
        }
    }
}