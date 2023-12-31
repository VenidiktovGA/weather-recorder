package ru.venidiktov.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.venidiktov.dto.request.SensorRq;
import ru.venidiktov.dto.response.RegistrationRs;
import ru.venidiktov.exception.SensorException;
import ru.venidiktov.service.SensorsService;

@AllArgsConstructor
@RestController
@RequestMapping("/sensors")
@Tag(name = "Сенсор", description = "Работа с сенсором")
public class SensorsController {

    private SensorsService sensorsService;

    @PostMapping(path = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Зарегистрировать сенсор")
    public RegistrationRs registration(@RequestBody @Valid SensorRq request, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) throw new SensorException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        return sensorsService.registrationSensor(request);
    }
}
