package co.m11.meisaicsv.parser.MitsuiSumitomoCard;

import co.m11.meisaicsv.common.CsvParseResult;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static co.m11.meisaicsv.common.MeisaiCsvUtil.getClassPathResource;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.jupiter.api.Assertions.*;

public class TestMitsuiSumitomoCard {

    @Test
    void testToCsvRecord() {

        // ご利用日,ご利用店名,ご利用金額,支払い区分,分割回数,お支払い金額,備考
        ParserMitsuiSumitomoCard parser = new ParserMitsuiSumitomoCard();
        List<String> list = Lists.newArrayList(
                "2018/01/23","ご利用店名","1,234","支払い区分", "分割回数","2,345","備考");
        String[] arr = list.toArray(new String[list.size()]);
        CsvRecordMitsuiSumitomoCard record = parser.toCsvRecord(arr);
        assertEquals(parse("2018/1/23", ofPattern("yyyy/M/d")), record.getRiyoubi());
        assertEquals(arr[1], record.getSyousai1());
        assertEquals(1234L, (long) record.getPrice());
        assertEquals(arr[3], record.getShiharaiKubun());
        assertEquals(arr[4], record.getBunkatsuKaisu());
        assertEquals(2345L, (long) record.getOshiharaiKingaku());
        assertEquals(arr[6], record.getSyousai2());
        assertFalse(Strings.isNullOrEmpty(record.getId()));
    }

    @Test
    void testParse() throws Exception {
        ParserMitsuiSumitomoCard parser = new ParserMitsuiSumitomoCard();
        InputStream is = getClassPathResource("/csv/MitsuiSumitomoCard/MitsuiSumitomoCard.csv");
        CsvParseResult<CsvRecordMitsuiSumitomoCard> res = parser.parseCsv(is);
        assertEquals(3, res.getRecords().size());
        assertTrue(res.getErrors().isEmpty());

        // 2018/01/19,ＷＡＯＮ発行手数料,300,１,１,300,
        CsvRecordMitsuiSumitomoCard r1 = res.getRecords().get(2);
        assertEquals(parse("2018/1/19", ofPattern("yyyy/M/d")), r1.getRiyoubi());
        assertEquals("ＷＡＯＮ発行手数料", r1.getSyousai1());
        assertEquals(300L, (long) r1.getPrice());
        assertEquals("１", r1.getShiharaiKubun());
        assertEquals("１", r1.getBunkatsuKaisu());
        assertEquals(300L, (long) r1.getOshiharaiKingaku());
        assertEquals("", r1.getSyousai2());
        assertEquals("20180119-1", r1.getId());

        // 2017/12/11,タイムズカープラス,1550,,,,２０１７１２カ―ドハツコウリヨウ
        CsvRecordMitsuiSumitomoCard r2 = res.getRecords().get(3);
        assertEquals(parse("2017/12/11", ofPattern("yyyy/M/d")), r2.getRiyoubi());
        assertEquals("タイムズカープラス", r2.getSyousai1());
        assertEquals(1550L, (long) r2.getPrice());
        assertEquals("", r2.getShiharaiKubun());
        assertEquals("", r2.getBunkatsuKaisu());
        assertNull(r2.getOshiharaiKingaku());
        assertEquals("２０１７１２カ―ドハツコウリヨウ", r2.getSyousai2());
        assertEquals("20171211-1", r2.getId());

        // 2018/01/01,ＡＭＡＺＯＮ．ＣＯ．ＪＰ,3197,,,,
        CsvRecordMitsuiSumitomoCard r3 = res.getRecords().get(4);
        assertEquals(parse("2018/1/1", ofPattern("yyyy/M/d")), r3.getRiyoubi());
        assertEquals("ＡＭＡＺＯＮ．ＣＯ．ＪＰ", r3.getSyousai1());
        assertEquals(3197L, (long) r3.getPrice());
        assertEquals("", r3.getShiharaiKubun());
        assertEquals("", r3.getBunkatsuKaisu());
        assertNull(r3.getOshiharaiKingaku());
        assertEquals("", r3.getSyousai2());
        assertEquals("20180101-1", r3.getId());
    }
}
