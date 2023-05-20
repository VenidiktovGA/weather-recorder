package ru.venidiktov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ru.venidiktov.dto.SensorsDto;
import ru.venidiktov.model.Sensors;
import ru.venidiktov.repository.SensorsRepository;
import ru.venidiktov.service.SensorsService;
import ru.venidiktov.validator.SensorsValidate;


@WebMvcTest(controllers = SensorsController.class)
@Import({SensorsService.class, SensorsValidate.class})
class SensorsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SensorsRepository sensorsRepository;

    @Test
    void registrationSensor_returnCode200_ifSensorDtoCorrect() throws Exception {
        SensorsDto sensorsDto = new SensorsDto("Сенсор ВЕКТОР");

        mockMvc.perform(post("/sensor/registration")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(sensorsDto)))
                .andExpect(status().isOk());
    }

    @Test
    void registrationSensor_returnCode400_ifSensorDtoNotValid() throws Exception {
        SensorsDto sensorsDto = new SensorsDto("Се");

        mockMvc.perform(post("/sensor/registration")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(sensorsDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", equalTo("Имя сенсора должно быть от 3 до 30 символов")));
    }

    @Test
    void registrationSensor_returnCode400_ifSensorWithNameExists() throws Exception {
        SensorsDto sensorsDto = new SensorsDto("Сенсор №23");
        Mockito.doReturn(Optional.of(new Sensors())).when(sensorsRepository).findByName(anyString());

        mockMvc.perform(post("/sensor/registration")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(sensorsDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", equalTo("Сенсор с именем 'Сенсор №23' уже есть")));
    }
}