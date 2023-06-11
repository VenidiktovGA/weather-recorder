package ru.venidiktov.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.venidiktov.dto.RegistrationSensorRq;
import ru.venidiktov.dto.RegistrationSensorRs;
import ru.venidiktov.exception.SensorException;
import ru.venidiktov.service.SensorsService;

@AllArgsConstructor
@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private SensorsService sensorsService;

    @PostMapping(path = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Сохранить JSON в базе данных")
    public RegistrationSensorRs registration(@RequestBody @Valid RegistrationSensorRq registrationSensorRq, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) throw new SensorException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        return sensorsService.registrationSensor(registrationSensorRq);
    }
}
