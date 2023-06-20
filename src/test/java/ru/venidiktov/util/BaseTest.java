package ru.venidiktov.util;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.venidiktov.integration.PostgresContextInitializer;
import ru.venidiktov.repository.MeasurementsRepository;
import ru.venidiktov.repository.SensorsRepository;
import ru.venidiktov.service.MeasurementsService;
import ru.venidiktov.service.SensorsService;
import ru.venidiktov.validator.SensorsValidate;

@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@Import({SensorsService.class, MeasurementsService.class, SensorsValidate.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = {PostgresContextInitializer.class})
public class BaseTest {

    @SpyBean
    public SensorsService sensorsService;

    @SpyBean
    public MeasurementsService measurementsService;

    @SpyBean
    public SensorsValidate sensorsValidate;

    @SpyBean
    public SensorsRepository sensorsRepository;

    @SpyBean
    public MeasurementsRepository measurementsRepository;
}
