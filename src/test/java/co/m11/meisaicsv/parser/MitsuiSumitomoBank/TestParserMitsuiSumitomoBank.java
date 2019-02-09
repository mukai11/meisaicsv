package co.m11.meisaicsv.parser.MitsuiSumitomoBank;

import co.m11.meisaicsv.common.CsvParseResult;
import co.m11.meisaicsv.common.CsvRecord;
import co.m11.meisaicsv.common.Util;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.jupiter.api.Assertions.*;

public class TestParserMitsuiSumitomoBank {

    @Test
    void parseJapaneseRiyoubi() {
        ParserMitsuiSumitomoBank parser = new ParserMitsuiSumitomoBank();
        LocalDate date = parser.parseJapaneseRiyoubi("H30.01.15");
        assertEquals(parse("2018/1/15", ofPattern("yyyy/M/d")), date);

        date = parser.parseJapaneseRiyoubi("H1.12.31");
        assertEquals(parse("1989/12/31", ofPattern("yyyy/M/d")), date);
    }

    @Test
    void testToCsvRecord() {
        // "年月日（和暦）","お引出し","お預入れ","お取り扱い内容","残高"
        ParserMitsuiSumitomoBank parser = new ParserMitsuiSumitomoBank();
        List<String> list = Lists.newArrayList("H30.01.15", "100", "", "お取り扱い内容", "9876");

        // 出金
        String[] arr = list.toArray(new String[list.size()]);
        CsvRecord record = parser.toCsvRecord(arr);
        assertion(arr, record);
        assertEquals(100L, (long) record.getPrice());

        // 入金
        list.set(1, "");
        list.set(2, "200");
        arr = list.toArray(new String[list.size()]);
        record = parser.toCsvRecord(arr);
        assertion(arr, record);
        assertEquals(-200L, (long) record.getPrice());
    }

    private void assertion(String[] arr, CsvRecord record) {
        assertEquals(parse("2018/1/15", ofPattern("yyyy/M/d")), record.getRiyoubi());
        assertEquals(arr[3], record.getSyousai1());
        assertNull(record.getSyousai2());
        assertNull(record.getMemo());
        assertEquals(Long.parseLong(arr[4]), (long) record.getBankBalance());
        assertFalse(Strings.isNullOrEmpty(record.getId()));
    }

    @Test
    void testParse() throws Exception {
        ParserMitsuiSumitomoBank parser = new ParserMitsuiSumitomoBank();
        InputStream is = Util.getClassPathResource("/csv/MitsuiSumitomoBank/MitsuiSumitomoBank.csv");
        CsvParseResult<CsvRecord> res = parser.parseCsv(is);
        assertEquals(2, res.getRecords().size());
        assertTrue(res.getErrors().isEmpty());

        // H30.01.15,,5000,"振込　カ−ドｷﾔﾏ ｸﾛ",35432
        CsvRecord r1 = res.getRecords().get(2);
        assertEquals(parse("2018/1/15", ofPattern("yyyy/M/d")), r1.getRiyoubi());
        assertEquals(-5000L, (long) r1.getPrice());
        assertEquals("振込　カ−ドｷﾔﾏ ｸﾛ", r1.getSyousai1());
        assertEquals(35432L, (long) r1.getBankBalance());
        assertEquals("20180115-1", r1.getId());

        // H30.01.17,15900,,"カード振込　ｶﾞｸ) ",19532
        CsvRecord r2 = res.getRecords().get(3);
        assertEquals(parse("2018/1/17", ofPattern("yyyy/M/d")), r2.getRiyoubi());
        assertEquals(15900L, (long) r2.getPrice());
        assertEquals("カード振込　ｶﾞｸ) ", r2.getSyousai1());
        assertEquals(19532L, (long) r2.getBankBalance());
        assertEquals("20180117-1", r2.getId());    }
}