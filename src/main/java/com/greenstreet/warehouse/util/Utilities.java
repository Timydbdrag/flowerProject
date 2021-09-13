package com.greenstreet.warehouse.util;

import com.greenstreet.warehouse.config.Constants;

import java.time.Instant;
import java.util.Date;

public class Utilities {

    /**
     * возвращает дату из рассчета (текущая дата + количество минут переданное в параметре)
     */
    public static Date getExpiredDate(long minutes) {
        return Date.from(Instant.now().plusSeconds(60 * minutes));
    }

    /**
     * Проверка пароля на null и длинну
     */
    public static boolean isPasswordLengthInvalid(String password) {
        return (
                password == null ||
                        password.length() < Constants.PASSWORD_MIN_LENGTH ||
                        password.length() > Constants.PASSWORD_MAX_LENGTH
        );
    }
}
