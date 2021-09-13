package com.greenstreet.warehouse.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ApiExceptionHandlerTest {

    @InjectMocks
    ApiExceptionHandler apiExceptionHandler;

    @Test
    void handler() {
        LocalDateTime localDateTimeNow = LocalDateTime.now();

        try (MockedStatic<LocalDateTime> localDateMockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            localDateMockedStatic.when(LocalDateTime::now).thenReturn(localDateTimeNow);

            assertThat(LocalDateTime.now()).isEqualTo(localDateTimeNow);

            HttpStatus badRequest = HttpStatus.BAD_REQUEST;
            ApiResponseException apiResponseException =
                    new ApiResponseException("Test", badRequest, LocalDateTime.now());

            ResponseEntity<ApiResponseException> expected = new ResponseEntity<>(apiResponseException, badRequest);

            assertThat(apiExceptionHandler.handler(new ApiRequestException("Test"))).isEqualTo(expected);
        }
    }
}