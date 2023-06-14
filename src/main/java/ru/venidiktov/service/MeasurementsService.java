package ru.venidiktov.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.venidiktov.dto.MeasurementsRq;
import ru.venidiktov.repository.MeasurementsRepository;

@AllArgsConstructor
@Service
public class MeasurementsService {

    private SensorsService sensorsService;

    private MeasurementsRepository measurementsRepository;

    public void addMeasurements(MeasurementsRq request) {
        var measurements = request.toMeasurements(sensorsService);
        measurementsRepository.save(measurements);
    }
}
