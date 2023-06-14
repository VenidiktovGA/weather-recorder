package ru.venidiktov.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.venidiktov.dto.MeasurementsRq;
import ru.venidiktov.exception.MeasurementsException;
import ru.venidiktov.service.MeasurementsService;

@AllArgsConstructor
@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private MeasurementsService measurementsService;

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addMeasurements(@RequestBody @Valid MeasurementsRq request, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) throw new MeasurementsException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        measurementsService.addMeasurements(request);
    }
}