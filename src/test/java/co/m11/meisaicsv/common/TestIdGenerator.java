package co.m11.meisaicsv.common;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIdGenerator {

    @Test
    public void testIdGenerator() {
        IdGenerator idGenerator = new IdGenerator();
        LocalDate riyoubi1 = parse("2018/1/23", ofPattern("yyyy/M/d"));
        LocalDate riyoubi2 = parse("2018/1/24", ofPattern("yyyy/M/d"));
        assertEquals("20180123-1", idGenerator.getId(riyoubi1));
        assertEquals("20180123-2", idGenerator.getId(riyoubi1));
        assertEquals("20180124-1", idGenerator.getId(riyoubi2));
    }
}
