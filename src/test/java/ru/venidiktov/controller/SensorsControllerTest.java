package ru.venidiktov.controller;

import java.util.Optional;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ru.venidiktov.dto.RegistrationSensorRq;
import ru.venidiktov.dto.RegistrationSensorRs;
import ru.venidiktov.exception.SensorException;
import ru.venidiktov.model.Sensors;
import ru.venidiktov.util.BaseMvcTest;

class SensorsControllerTest extends BaseMvcTest {

    @Test
    void registrationSensor_returnCode200_ifSensorDtoCorrect() throws Exception {
        RegistrationSensorRq registrationSensorRq = new RegistrationSensorRq("Сенсор ВЕКТОР");

        RegistrationSensorRs expectedResponse = new RegistrationSensorRs("Сенсор с именем 'Сенсор ВЕКТОР' успешно зарегистрирован");

        mockMvc.perform(post("/sensors/registration")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(registrationSensorRq)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Nested
    class Validation {
        @ParameterizedTest
        @ValueSource(strings = {"C", "Ce", " ", "    ", "  C     ", "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"})
        void registrationSensor_returnCode400_ifSensorDtoNotValid(String sensorName) throws Exception {
            mockMvc.perform(post("/sensors/registration")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(String.format("{\"name\": \"%s\"}", sensorName)))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("$.message", equalTo("Имя сенсора должно быть от 3 до 30 символов")))
                    .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(SensorException.class));
        }

        @Test
        void registrationSensor_returnCode400_ifFieldNameIsNull() throws Exception {
            mockMvc.perform(post("/sensors/registration")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content("{}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("$.message", equalTo("Поле name обязательно")))
                    .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(SensorException.class));
        }

        @Test
        void registrationSensor_returnCode400_ifSensorWithNameExists() throws Exception {
            String existSensorName = "Сенсор №23";
            Mockito.doReturn(Optional.of(new Sensors(existSensorName))).when(sensorsRepository).findByNameIgnoreCase(existSensorName);

            mockMvc.perform(post("/sensors/registration")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(String.format("{\"name\": \"%s\"}", existSensorName)))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("$.message", equalTo("Сенсор с именем 'Сенсор №23' уже есть")))
                    .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(SensorException.class));
        }
    }
}