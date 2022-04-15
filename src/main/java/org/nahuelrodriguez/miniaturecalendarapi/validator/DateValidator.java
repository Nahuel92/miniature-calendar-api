package org.nahuelrodriguez.miniaturecalendarapi.validator;

import org.nahuelrodriguez.miniaturecalendarapi.utils.ApiDateUtils;
import org.nahuelrodriguez.miniaturecalendarapi.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.function.BiPredicate;

public @Component
class DateValidator {
    private static final Logger LOG = LoggerFactory.getLogger(DateValidator.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
    private static final String errorMessage = "Date must be after {0} !";

    public void validateFormat(final String date, final Locale locale) {
        LOG.debug("Validating date");
        final var parsedDate = Utils.tryAndGetOrThrow(() -> LocalDate.parse(date, ApiDateUtils.DATE_FORMAT));
        final var formattedInitialDate = DATE_TIME_FORMATTER
                .withLocale(locale)
                .format(ApiDateUtils.START_DATE);
        validateDateBoundaries(LocalDate::isBefore, parsedDate, ApiDateUtils.START_DATE, MessageFormat.format(errorMessage, formattedInitialDate));
        validateDateBoundaries(LocalDate::isAfter, parsedDate, LocalDate.now(), "Date must be before tomorrow!");
    }

    private void validateDateBoundaries(final BiPredicate<LocalDate, LocalDate> boundary, final LocalDate actualDate,
                                        final LocalDate targetDate, final String errorMessage) {
        if (boundary.test(actualDate, targetDate)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
