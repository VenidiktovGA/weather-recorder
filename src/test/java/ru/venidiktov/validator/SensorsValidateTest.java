//package ru.venidiktov.validator;
//
//import java.util.Optional;
//import static org.junit.jupiter.api.Assertions.assertAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import static org.mockito.ArgumentMatchers.anyString;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.Spy;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.SpyBean;
//import org.springframework.validation.BeanPropertyBindingResult;
//import org.springframework.validation.BindingResult;
//import ru.venidiktov.dto.SensorsDto;
//import ru.venidiktov.model.Sensors;
//import ru.venidiktov.repository.SensorsRepository;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@ExtendWith(MockitoExtension.class)
//class SensorsValidateTest {
//    @Mock
//    private SensorsRepository sensorsRepository;
//
////    @Mock
////    private BeanPropertyBindingResult errors;
//    @Mock
//    Object object;
//
//    @MockBean
//    BindingResult errors;
//
//    @InjectMocks
//    SensorsValidate sensorsValidate;
//
//    @BeforeEach
//    void init() {
//        errors = new BeanPropertyBindingResult(object, "obj");
//    }
//
//
//    @Test
//    void validate_shouldValid_idSensorsIsValid() {
//        Mockito.doReturn(Optional.empty()).when(sensorsRepository).findByName(anyString());
//
//        sensorsValidate.validate(new SensorsDto("Новый сенсор"), errors);
//
//        Mockito.verify(errors, Mockito.never()).rejectValue(anyString(), anyString());
//        assertThat(errors.getAllErrors()).isEmpty();
//    }
//
//    @Test
//    void validate_shouldErrorValid_ifSensorsIsNotValid() {
//        Mockito.doReturn(Optional.of(new Sensors())).when(sensorsRepository).findByName(anyString());
//
//        sensorsValidate.validate(new SensorsDto("Существующий сенсор"), errors);
//
//        Mockito.verify(errors).rejectValue(anyString(), anyString());
//        assertAll(
//                () -> assertThat(errors.getAllErrors()).isNotEmpty(),
//                () -> assertThat(errors.getAllErrors().get(0).getDefaultMessage()).isEqualTo("Сенсор с таким именем уже есть")
//        );
//    }
//}