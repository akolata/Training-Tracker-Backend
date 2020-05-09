package pl.akolata.trainingtracker.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@UtilityClass
public final class DateUtil {
    public static final ZoneId SYSTEM_ZONE_ID = ZoneId.systemDefault();

    public static LocalDateTime toLocalDateTime(OffsetDateTime offsetDateTime) {
        return offsetDateTime == null ? null : offsetDateTime.atZoneSameInstant(SYSTEM_ZONE_ID).toLocalDateTime();
    }

    public static LocalDate toLocalDate(OffsetDateTime offsetDateTime) {
        return offsetDateTime == null ? null : offsetDateTime.atZoneSameInstant(SYSTEM_ZONE_ID).toLocalDate();
    }
}
