package ru.venidiktov.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.venidiktov.dto.request.MeasurementRq;
import ru.venidiktov.dto.response.MeasurementRs;
import ru.venidiktov.model.Measurements;
import ru.venidiktov.repository.MeasurementsRepository;

@AllArgsConstructor
@Service
public class MeasurementsService {

    private SensorsService sensorsService;

    private MeasurementsRepository measurementsRepository;

    public void addMeasurements(MeasurementRq request) {
        var measurements = request.toMeasurements(sensorsService);
        measurementsRepository.save(measurements);
    }

    public List<MeasurementRs> getAllMeasurements() {
        return measurementsRepository.findAll(Sort.by(Sort.Direction.DESC, "created"))
                .stream().map(Measurements::toMeasurementRs).toList();
    }
}
