package com.footystars.foot8.buisness.service;

import com.footystars.foot8.utils.ZodiacSign;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class ZodiacService {

    public ZodiacSign getZodiacSign(@NotNull String birthDate) {
        LocalDate date = LocalDate.parse(birthDate);
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        return Arrays.stream(ZodiacSign.values())
                .filter(sign -> (month == sign.getStartMonth() && day >= sign.getStartDay()) ||
                        (month == (sign == ZodiacSign.CAPRICORN ? 1 : sign.getStartMonth() + 1) &&
                                day <= (sign == ZodiacSign.CAPRICORN ? 19 : sign.getEndDay())))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid date"));
    }

}