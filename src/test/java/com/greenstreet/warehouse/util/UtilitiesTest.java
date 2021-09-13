package com.greenstreet.warehouse.util;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilitiesTest {

    @Test
    void getExpiredDate() {
        int testParam = 60;
        Instant now = Instant.now();
        Date expected = Date.from(now.plusSeconds(60 * testParam));

        try (MockedStatic<Instant> instantMockedStatic = Mockito.mockStatic(Instant.class)) {
            instantMockedStatic.when(Instant::now).thenReturn(now);

            try (MockedStatic<Utilities> theMock = Mockito.mockStatic(Utilities.class)) {
                theMock.when(() -> Utilities.getExpiredDate(testParam)).thenReturn(expected);

                assertEquals(Utilities.getExpiredDate(testParam), expected);
            }
        }
    }

    @Test
    void isPasswordLengthInvalid() {
    }
}