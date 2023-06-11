package ru.venidiktov.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.venidiktov.exception.SensorException;
import ru.venidiktov.repository.SensorsRepository;

@Component
@AllArgsConstructor
public class SensorsValidate {

    private SensorsRepository sensorsRepository;

    public void validate(String name) {
        if(sensorsRepository.findByNameIgnoreCase(name).isPresent()) {
            throw new SensorException(String.format("Сенсор с именем '%s' уже есть", name));
        }
    }
}
