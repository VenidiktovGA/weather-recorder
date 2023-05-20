package ru.venidiktov.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.venidiktov.dto.SensorsDto;
import ru.venidiktov.exception.SensorException;
import ru.venidiktov.service.SensorsService;

@AllArgsConstructor
@RestController
@RequestMapping("/sensor")
public class SensorsController {

    private SensorsService sensorsService;

    @PostMapping(path = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String registration(@RequestBody @Valid SensorsDto sensorsDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) throw new SensorException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        sensorsService.registrationSensor(sensorsDto);
        return "fdfd";
    }
}
