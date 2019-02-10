package co.m11.meisaicsv.common;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ofPattern;

public class IdGenerator {
    private Map<LocalDate, Integer> riyoubiIdMap = new HashMap<>();

    public String getId(LocalDate riyoubi) {
        if (!riyoubiIdMap.containsKey(riyoubi)) {
            riyoubiIdMap.put(riyoubi, 0);
        }
        int newId = riyoubiIdMap.get(riyoubi) + 1;
        riyoubiIdMap.put(riyoubi, newId);
        String ymd = riyoubi.format(ofPattern("yyyyMMdd"));
        return ymd + "-" + newId;
    }
}
