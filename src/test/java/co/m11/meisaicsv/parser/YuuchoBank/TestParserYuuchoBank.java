package co.m11.meisaicsv.parser.YuuchoBank;

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

public class TestParserYuuchoBank {

    @Test
    void testToCsvRecord() {
        // 取引日,入出金明細ＩＤ,受入金額（円）,払出金額（円）,詳細１,詳細２,現在（貸付）高,
        ParserYuuchoBank parser = new ParserYuuchoBank();
        List<String> list = Lists.newArrayList("20180528", "201805280000001", "", "1234", "詳細１", "詳細２", "5678");

        // 出金
        String[] arr = list.toArray(new String[list.size()]);
        CsvRecord record = parser.toCsvRecord(arr);
        assertion(arr, record);
        assertEquals(1234L, (long) record.getPrice());

        // 入金
        list.set(2, "3456");
        list.set(3, "");
        arr = list.toArray(new String[list.size()]);
        record = parser.toCsvRecord(arr);
        assertion(arr, record);
        assertEquals(-3456L, (long) record.getPrice());
    }

    private void assertion(String[] arr, CsvRecord record) {
        assertEquals(parse("2018/5/28", ofPattern("yyyy/M/d")), record.getRiyoubi());
        assertEquals(arr[1], record.getId());
        assertEquals(arr[4], record.getSyousai1());
        assertEquals(arr[5], record.getSyousai2());
        assertEquals(Long.parseLong(arr[6]), (long) record.getBankBalance());
    }

    @Test
    void testParse() throws Exception {
        ParserYuuchoBank parser = new ParserYuuchoBank();
        InputStream is = getClassPathResource("/csv/YuuchoBank/YuuchoBank.csv");
        CsvParseResult<CsvRecord> res = parser.parseCsv(is);
        assertEquals(2, res.getRecords().size());
        assertTrue(res.getErrors().isEmpty());

        // 20180720,201807200000001,3,,受取利子,,13345,
        CsvRecord r1 = res.getRecords().get(9);
        assertEquals(parse("2018/7/20", ofPattern("yyyy/M/d")), r1.getRiyoubi());
        assertEquals("201807200000001", r1.getId());
        assertEquals(-3L, (long) r1.getPrice());
        assertEquals("受取利子", r1.getSyousai1());
        assertEquals("", r1.getSyousai2());
        assertEquals(13345L, (long) r1.getBankBalance());

        // 20180720,201807200000002,,1000,送金,アリスカ−ド,12345,
        CsvRecord r2 = res.getRecords().get(10);
        assertEquals(parse("2018/7/20", ofPattern("yyyy/M/d")), r2.getRiyoubi());
        assertEquals("201807200000002", r2.getId());
        assertEquals(1000L, (long) r2.getPrice());
        assertEquals("送金", r2.getSyousai1());
        assertEquals("アリスカ−ド", r2.getSyousai2());
        assertEquals(12345L, (long) r2.getBankBalance());
    }
}
