package ru.venidiktov.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.venidiktov.dto.request.SensorRq;
import ru.venidiktov.dto.response.SensorRs;
import ru.venidiktov.exception.SensorException;
import ru.venidiktov.model.Sensors;
import ru.venidiktov.repository.SensorsRepository;
import ru.venidiktov.validator.SensorsValidate;

@AllArgsConstructor
@Service
public class SensorsService {

    private SensorsRepository sensorsRepository;

    private SensorsValidate sensorsValidate;

    public Sensors getSensorByNameIgnoreCase(String name) {
        return sensorsRepository.findByNameIgnoreCase(name).orElseThrow(
                () -> new SensorException(String.format("Сенсор с именем '%s' не найден", name))
        );
    }

    @Transactional
    public SensorRs registrationSensor(SensorRq sensorRq) {
        String newSensorName = sensorRq.getName();
        sensorsValidate.validate(newSensorName);
        sensorsRepository.save(Sensors.builder().name(newSensorName).build());
        return new SensorRs(String.format("Сенсор с именем '%s' успешно зарегистрирован", newSensorName));
    }
}
