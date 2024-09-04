package com.footystars.service.business;

import com.footystars.utils.ZodiacSign;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import static com.footystars.utils.LogsNames.DATA_FORMAT_1;
import static com.footystars.utils.LogsNames.DATA_FORMAT_2;
import static com.footystars.utils.LogsNames.DATA_FORMAT_3;
import static com.footystars.utils.LogsNames.DATA_FORMAT_4;
import static com.footystars.utils.LogsNames.DATA_FORMAT_5;
import static com.footystars.utils.LogsNames.DATA_FORMAT_6;
import static com.footystars.utils.LogsNames.INVALID_DATE;
import static com.footystars.utils.LogsNames.INVALID_DATE_FORMAT;

@Service
@RequiredArgsConstructor
public class ZodiacService {

    private final Logger logger = LoggerFactory.getLogger(ZodiacService.class);

    private static final DateTimeFormatter[] DATE_FORMATTERS = {
            DateTimeFormatter.ofPattern(DATA_FORMAT_1),
            DateTimeFormatter.ofPattern(DATA_FORMAT_2),
            DateTimeFormatter.ofPattern(DATA_FORMAT_3),
            DateTimeFormatter.ofPattern(DATA_FORMAT_4),
            DateTimeFormatter.ofPattern(DATA_FORMAT_5),
            DateTimeFormatter.ofPattern(DATA_FORMAT_6),
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
            } catch (DateTimeParseException e) {
                logger.error(e.getMessage(), e);
            }
        }
        throw new IllegalArgumentException(INVALID_DATE_FORMAT + birthDate);
    }
}