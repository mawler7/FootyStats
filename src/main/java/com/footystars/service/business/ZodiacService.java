package com.footystars.service.business;

import com.footystars.utils.ZodiacSign;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.SignStyle;
import java.util.Arrays;

import static com.footystars.utils.LogsNames.INVALID_DATE;
import static com.footystars.utils.LogsNames.INVALID_DATE_FORMAT;

/**
 * Service responsible for determining the zodiac sign based on a given birth date.
 */
@Service
@RequiredArgsConstructor
public class ZodiacService {

    private final Logger logger = LoggerFactory.getLogger(ZodiacService.class);

    /**
     * Supported date formats for parsing birthdates.
     */
    private static final DateTimeFormatter[] DATE_FORMATTERS = {
            new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-")
                    .appendValue(java.time.temporal.ChronoField.MONTH_OF_YEAR, 1, 2, SignStyle.NORMAL)
                    .appendLiteral('-')
                    .appendValue(java.time.temporal.ChronoField.DAY_OF_MONTH, 1, 2, SignStyle.NORMAL)
                    .toFormatter(),
            new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-")
                    .appendValue(java.time.temporal.ChronoField.DAY_OF_MONTH, 1, 2, SignStyle.NORMAL)
                    .appendLiteral('-')
                    .appendValue(java.time.temporal.ChronoField.MONTH_OF_YEAR, 1, 2, SignStyle.NORMAL)
                    .toFormatter()
    };

    /**
     * Determines the zodiac sign based on a given birthdate.
     *
     * @param birthDate The birthdate as a string in a supported format.
     * @return The corresponding {@link ZodiacSign}.
     * @throws IllegalArgumentException if the date format is invalid or if the zodiac sign cannot be determined.
     */
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
                logger.debug("Failed to parse date with formatter: {}", formatter, e);
            }
        }
        throw new IllegalArgumentException(INVALID_DATE_FORMAT + birthDate);
    }
}
