package ru.venidiktov.controller;


import java.util.Arrays;
import java.util.stream.Stream;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ru.venidiktov.dto.response.MeasurementRs;
import ru.venidiktov.dto.response.MeasurementSensorRs;
import ru.venidiktov.exception.MeasurementsException;
import ru.venidiktov.exception.SensorException;
import ru.venidiktov.util.BaseMvcTest;

class MeasurementsControllerTest extends BaseMvcTest {

    @Nested
    class AddMeasurements {
        @Test
        void return200_ifMeasurementsAdded () throws Exception {
            mockMvc.perform(post("/measurements/add")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content("{\"value\": \"50.5\", \"raining\": \"false\", \"sensor\": \"Вектор М\"}"))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @ParameterizedTest
        @ValueSource(doubles = {-101.0, -100.1, -100.01, -100.09, -100.5, -100.4, -100.04})
        void return400_ifFieldValueNotValid_valueLessThenMinRange(Double minValue) throws Exception {
            mockMvc.perform(post("/measurements/add")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(String.format("{\"value\": \"%f\", \"raining\": \"false\", \"sensor\": \"Вектор М\"}", minValue)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("$.message", equalTo("Минимальное значение показателя сенсора -100")))
                    .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(MeasurementsException.class));
        }

        @ParameterizedTest
        @ValueSource(doubles = {101.0, 100.1, 100.01, 100.09, 100.5, 100.4, 100.04})
        void return400_ifFieldValueNotValid_valueMoreThenMaxRange(Double maxValue) throws Exception {
            mockMvc.perform(post("/measurements/add")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(String.format("{\"value\": \"%f\", \"raining\": \"false\", \"sensor\": \"Вектор М\"}", maxValue)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("$.message", equalTo("Максимальное значение показателя сенсора 100")))
                    .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(MeasurementsException.class));
        }

        @ParameterizedTest
        @ValueSource(strings = {"C", "Ce", " ", "    ", "  C     ", "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"})
        void registrationSensor_returnCode400_ifSensorDtoNotValid(String sensorName) throws Exception {
            mockMvc.perform(post("/measurements/add")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(String.format("{\"value\": \"50.5\", \"raining\": \"false\", \"sensor\": \"%s\"}", sensorName)))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("$.message", equalTo("Имя сенсора должно быть от 3 до 30 символов")))
                    .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(SensorException.class));
        }

        @ParameterizedTest
        @MethodSource("getArgumentsNullForAddMeasurements")
        void return400_ifFieldNull(String json, String message) throws Exception {
            mockMvc.perform(post("/measurements/add")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(json))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("$.message", equalTo(message)))
                    .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(MeasurementsException.class));
        }

        static Stream<Arguments> getArgumentsNullForAddMeasurements() {
            return Stream.of(
                    Arguments.of("{\"raining\": \"false\", \"sensor\": \"Вектор М\"}", "Поле value обязательно"),
                    Arguments.of("{\"value\": \"50.5\", \"sensor\": \"Вектор М\"}", "Поле raining обязательно"),
                    Arguments.of("{\"value\": \"50.5\", \"raining\": \"false\"}", "Поле sensor обязательно")
            );
        }
    }

    @Nested
    class GetAllMeasurements {
        @Test
        void return200AndMeasurements_ifMeasurementsExist() throws Exception {
            var measurementsRs = new MeasurementRs(22.5, false, new MeasurementSensorRs("Вектор М"));
            Mockito.doReturn(Arrays.asList(measurementsRs)).when(measurementsService).getAllMeasurements();

            mockMvc.perform(get("/measurements"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("$[0].value", equalTo(22.5)))
                    .andExpect(jsonPath("$[0].raining", equalTo(false)))
                    .andExpect(jsonPath("$[0].sensor.name", equalTo("Вектор М")));
        }
        @Test
        void return200AndEmptyMeasurements_ifMeasurementsNotExist() throws Exception {
            mockMvc.perform(get("/measurements"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(content().json("[]"));
        }
    }
}