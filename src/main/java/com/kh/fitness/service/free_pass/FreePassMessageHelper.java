package com.kh.fitness.service.free_pass;


import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class FreePassMessageHelper {
    private static final String SUCCESS_MESSAGE = """
            Вы записаны на занятие по %s.
            Дата занятия %s
            c %s по %s
            """;
    private static final String POSTPONEMENT_NOTICE_MESSAGE = """
            Ваше пробное занятие по %s перенесено! 
            Дата занятия %s
            c %s по %s
            """;

    public static String prepareSuccessMessage(String training, LocalDate date, LocalTime start, LocalTime end) {
        return format(SUCCESS_MESSAGE, training, date, start, end);
    }

    public static String preparePostponementNoticeMessage(String training, LocalDate date, LocalTime start, LocalTime end) {
        return format(POSTPONEMENT_NOTICE_MESSAGE, training, date, start, end);
    }
}
