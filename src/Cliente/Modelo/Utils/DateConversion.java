package Cliente.Modelo.Utils;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class DateConversion {
    public static java.sql.Date conversionDate(LocalDate fecha) {
        if (fecha == null)
            return new java.sql.Date(System.currentTimeMillis());
        return java.sql.Date.valueOf(fecha);
    }

    public static java.sql.Time conversionTime(LocalTime fecha) {
        return java.sql.Time.valueOf(fecha);
    }

    public static LocalDate conversionLocalDate(java.sql.Date date) {
        return date.toLocalDate();
    }

    public static LocalTime conversionLocalTime(Time time) {
        return time.toLocalTime();
    }
}
