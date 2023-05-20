package ru.venidiktov.service;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.venidiktov.dto.SensorsDto;
import ru.venidiktov.model.Sensors;
import ru.venidiktov.validator.SensorsValidate;

@AllArgsConstructor
@Service
public class SensorsService {

    private SensorsValidate sensorsValidate;

    public Sensors registrationSensor(SensorsDto sensorsDto) {
        sensorsValidate.validate(sensorsDto.getName());
        return Sensors.builder().name("ddfdf").registrationDate(LocalDateTime.now()).build();
    }
}
