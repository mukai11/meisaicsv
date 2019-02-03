package co.m11.meisaicsv.parser.MitsubishiUfjBank;

import co.m11.meisaicsv.common.CsvParseResult;
import co.m11.meisaicsv.common.Util;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestParserMitsubishiUfjBank {

    @Test
    void testToCsvRecord() {
        // 日付,摘要,摘要内容,支払い金額,預かり金額,差引残高,メモ,未資金化区分,入払区分
        ParserMitsubishiUfjBank parser = new ParserMitsubishiUfjBank();
        List<String> list = Lists.newArrayList("2000/12/23", "摘要", "摘要内容", "4,5", "", "678", "メモ", "未資金化区分", "入払区分");

        // 出金
        String[] arr = list.toArray(new String[list.size()]);
        CsvRecordMitsubishiUfjBank record = parser.toCsvRecord(arr);
        assertion(arr, record);
        assertEquals(45L, (long) record.getPrice());

        // 入金
        list.set(3, "");
        list.set(4, "987");
        arr = list.toArray(new String[list.size()]);
        record = parser.toCsvRecord(arr);
        assertion(arr, record);
        assertEquals(-987, (long) record.getPrice());
    }

    private void assertion(String[] arr, CsvRecordMitsubishiUfjBank record) {
        assertEquals(parse(arr[0], ofPattern("yyyy/M/d")).atStartOfDay(), record.getRiyoubi());
        assertEquals(arr[1], record.getSyousai1());
        assertEquals(arr[2], record.getSyousai2());
        assertEquals(Long.parseLong(arr[5]), (long) record.getBankBalance());
        assertEquals(arr[6], record.getMemo());
        assertEquals(arr[7], record.getMishikinkaKubun());
        assertEquals(arr[8], record.getIribaraiKubun());
        assertFalse(Strings.isNullOrEmpty(record.getId()));
    }

    @Test
    void testParse() throws Exception {
        ParserMitsubishiUfjBank parser = new ParserMitsubishiUfjBank();
        InputStream is = Util.getClassPathResource("/csv/MitsubishiUfjBank/MitsubishiUfjBank.csv");
        CsvParseResult<CsvRecordMitsubishiUfjBank> res = parser.parseCsv(is);
        assertEquals(2, res.getRecords().size());
        assertTrue(res.getErrors().isEmpty());

        // "2018/1/9","給料","","","230,090","414,679","","","振替入金"
        CsvRecordMitsubishiUfjBank r1 = res.getRecords().get(2);
        assertEquals(parse("2018/1/9", ofPattern("yyyy/M/d")).atStartOfDay(), r1.getRiyoubi());
        assertEquals("給料", r1.getSyousai1());
        assertEquals("", r1.getSyousai2());
        assertEquals(-230090L, (long) r1.getPrice());
        assertEquals(414679L, (long) r1.getBankBalance());
        assertEquals("", r1.getMemo());
        assertEquals("", r1.getMishikinkaKubun());
        assertEquals("振替入金", r1.getIribaraiKubun());
        assertFalse(Strings.isNullOrEmpty(r1.getId()));

        // "2018/1/21","ＪＣＢ","ＪＣＢ）セブンカ−ド","45,252","","369,427","つかいすぎ","","振替支払い"
        CsvRecordMitsubishiUfjBank r2 = res.getRecords().get(3);
        assertEquals(parse("2018/1/21", ofPattern("yyyy/M/d")).atStartOfDay(), r2.getRiyoubi());
        assertEquals("ＪＣＢ", r2.getSyousai1());
        assertEquals("ＪＣＢ）セブンカ−ド", r2.getSyousai2());
        assertEquals(45252L, (long) r2.getPrice());
        assertEquals(369427, (long) r2.getBankBalance());
        assertEquals("つかいすぎ", r2.getMemo());
        assertEquals("", r2.getMishikinkaKubun());
        assertEquals("振替支払い", r2.getIribaraiKubun());
        assertFalse(Strings.isNullOrEmpty(r2.getId()));
    }
}