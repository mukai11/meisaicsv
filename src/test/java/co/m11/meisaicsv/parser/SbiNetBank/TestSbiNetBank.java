package co.m11.meisaicsv.parser.SbiNetBank;

import co.m11.meisaicsv.common.CsvParseResult;
import co.m11.meisaicsv.common.CsvRecord;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static co.m11.meisaicsv.common.MeisaiCsvUtil.getClassPathResource;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSbiNetBank {

    @Test
    void testToCsvRecord() {
        // "日付","内容","出金金額(円)","入金金額(円)","残高(円)","メモ"
        ParserSbiNetBank parser = new ParserSbiNetBank();
        List<String> list = Lists.newArrayList("2018/05/28", "内容", "123", "", "234", "メモ");

        // 出金
        String[] arr = list.toArray(new String[list.size()]);
        CsvRecord record = parser.toCsvRecord(arr);
        assertion(arr, record);
        assertEquals(123L, (long) record.getPrice());

        // 入金
        list.set(2, "");
        list.set(3, "345");
        arr = list.toArray(new String[list.size()]);
        record = parser.toCsvRecord(arr);
        assertion(arr, record);
        assertEquals(-345L, (long) record.getPrice());
    }

    private void assertion(String[] arr, CsvRecord record) {
        assertEquals(parse(arr[0], ofPattern("yyyy/MM/dd")), record.getRiyoubi());
        assertEquals(arr[1], record.getSyousai1());
        assertEquals(Long.parseLong(arr[4]), (long) record.getBankBalance());
        assertEquals(arr[5], record.getMemo());
    }

    @Test
    void testParse() throws Exception {
        ParserSbiNetBank parser = new ParserSbiNetBank();
        InputStream is = getClassPathResource("/csv/SbiNetBank/SbiNetBank.csv");
        CsvParseResult<CsvRecord> res = parser.parseCsv(is);
        assertEquals(3, res.getRecords().size());
        assertTrue(res.getErrors().isEmpty());

        // "2019/01/20","利息",,"1","20,001","-"
        CsvRecord r1 = res.getRecords().get(2);
        assertEquals(parse("2019/1/20", ofPattern("yyyy/M/d")), r1.getRiyoubi());
        assertEquals("利息", r1.getSyousai1());
        assertEquals(-1L, (long) r1.getPrice());
        assertEquals(20001L, (long) r1.getBankBalance());
        assertEquals("-", r1.getMemo());
        assertEquals("20190120-1", r1.getId());

        // "2019/01/10","振込＊トクガワイエヤス","10,000",,"10,001","-"
        CsvRecord r2 = res.getRecords().get(3);
        assertEquals(parse("2019/1/10", ofPattern("yyyy/M/d")), r2.getRiyoubi());
        assertEquals("振込＊トクガワイエヤス", r2.getSyousai1());
        assertEquals(10000L, (long) r2.getPrice());
        assertEquals(10001L, (long) r2.getBankBalance());
        assertEquals("-", r2.getMemo());
        assertEquals("20190110-1", r2.getId());

        // "2019/01/10","振込＊トヨトミヒデヨシ","2,000",,"8,001","-"
        CsvRecord r3 = res.getRecords().get(4);
        assertEquals(parse("2019/1/10", ofPattern("yyyy/M/d")), r3.getRiyoubi());
        assertEquals("振込＊トヨトミヒデヨシ", r3.getSyousai1());
        assertEquals(2000L, (long) r3.getPrice());
        assertEquals(8001L, (long) r3.getBankBalance());
        assertEquals("-", r3.getMemo());
        assertEquals("20190110-2", r3.getId());
    }
}
