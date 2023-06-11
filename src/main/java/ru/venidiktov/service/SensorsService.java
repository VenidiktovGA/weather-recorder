package ru.venidiktov.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.venidiktov.dto.RegistrationSensorRq;
import ru.venidiktov.dto.RegistrationSensorRs;
import ru.venidiktov.model.Sensors;
import ru.venidiktov.repository.SensorsRepository;
import ru.venidiktov.validator.SensorsValidate;

@AllArgsConstructor
@Service
public class SensorsService {

    private SensorsRepository sensorsRepository;

    private SensorsValidate sensorsValidate;

    @Transactional
    public RegistrationSensorRs registrationSensor(RegistrationSensorRq registrationSensorRq) {
        String newSensorName = registrationSensorRq.getName();
        sensorsValidate.validate(newSensorName);
        sensorsRepository.save(Sensors.builder().name(newSensorName).build());
        return new RegistrationSensorRs(String.format("Сенсор с именем '%s' успешно зарегистрирован", newSensorName));
    }
}
