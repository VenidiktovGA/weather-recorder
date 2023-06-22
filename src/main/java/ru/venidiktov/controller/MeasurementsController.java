package ru.venidiktov.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.venidiktov.dto.request.MeasurementRq;
import ru.venidiktov.dto.response.MeasurementRs;
import ru.venidiktov.exception.MeasurementsException;
import ru.venidiktov.service.MeasurementsService;

@AllArgsConstructor
@RestController
@RequestMapping("/measurements")
@Tag(name = "Измерения", description = "Работа с измерениями")
public class MeasurementsController {

    private MeasurementsService measurementsService;

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Добавить измерение")
    public void addMeasurements(@RequestBody @Valid MeasurementRq request, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) throw new MeasurementsException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        measurementsService.addMeasurements(request);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получить все измерения")
    public List<MeasurementRs> getAllMeasurements() {
        return measurementsService.getAllMeasurements();
    }

    @GetMapping(path = "/rainyDaysCount", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получить все дождливые дни")
    public Long getRainyDaysCount() {
        return measurementsService.getCountRainingDays();
    }
}
