package ru.venidiktov.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import ru.venidiktov.repository.SensorsRepository;
import ru.venidiktov.service.MeasurementsService;
import ru.venidiktov.service.SensorsService;
import ru.venidiktov.validator.SensorsValidate;

@WebMvcTest
@Import({SensorsService.class, SensorsValidate.class})
public class BaseMvcTest {

    @SpyBean
    public MockMvc mockMvc;

    @SpyBean
    public ObjectMapper objectMapper;

    @MockBean
    public SensorsRepository sensorsRepository;

    @MockBean
    public MeasurementsService measurementsService;
}
