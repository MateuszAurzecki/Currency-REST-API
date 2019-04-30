package pl.b2b.Nbp.validation;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;
import pl.b2b.Nbp.constant.CurrencyCode;
import pl.b2b.Nbp.exception.InvalidCodeException;
import pl.b2b.Nbp.exception.InvalidDateException;
import pl.b2b.Nbp.exception.InvalidDateRangeException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class NbpApiValidation {

    public static boolean validateDateFormat(String start) {
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
        if (!pattern.matcher(start).matches()) {
            throw new InvalidDateException();
        }
        return true;
    }

    public static boolean validateDateRange(String start, String end) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDateStart = LocalDate.parse(start, formatter);
        LocalDate localDateEnd = LocalDate.parse(end, formatter);
        long daysBetween = DAYS.between(localDateStart, localDateEnd);
        if (daysBetween > 93) {
            throw new InvalidDateRangeException();
        }
        return true;
    }


    public static boolean validateCurrencyCode(String code) {
        if (!EnumUtils.isValidEnum(CurrencyCode.class, code)) {
            throw new InvalidCodeException();
        }
        return true;
    }

}