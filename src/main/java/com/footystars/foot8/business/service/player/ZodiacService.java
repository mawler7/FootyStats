package com.footystars.foot8.business.service.player;

import com.footystars.foot8.utils.ZodiacSign;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import static com.footystars.foot8.utils.LogsNames.INVALID_DATE;
import static com.footystars.foot8.utils.LogsNames.INVALID_DATE_FORMAT;

@Service
@RequiredArgsConstructor
public class ZodiacService {

    private static final DateTimeFormatter[] DATE_FORMATTERS = {
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy-dd-MM"),
            DateTimeFormatter.ofPattern("yyyy-dd-M"),
            DateTimeFormatter.ofPattern("yyyy-M-dd"),
            DateTimeFormatter.ofPattern("yyyy-MM-d"),
            DateTimeFormatter.ofPattern("yyyy-M-d"),
    };

    public ZodiacSign getZodiacSign(@NotNull String birthDate) {
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                LocalDate date = LocalDate.parse(birthDate, formatter);
                int month = date.getMonthValue();
                int day = date.getDayOfMonth();

                return Arrays.stream(ZodiacSign.values())
                        .filter(sign -> (month == sign.getStartMonth() && day >= sign.getStartDay()) ||
                                (month == (sign == ZodiacSign.CAPRICORN ? 1 : sign.getStartMonth() + 1) &&
                                        day <= (sign == ZodiacSign.CAPRICORN ? 19 : sign.getEndDay())))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException(INVALID_DATE));
            } catch (DateTimeParseException ignored) {
            }
        }

        throw new IllegalArgumentException(INVALID_DATE_FORMAT + birthDate);
    }
}