package co.m11.meisaicsv.parser.SevenCard;

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

public class TestParserSevenCard {

    @Test
    void testToCsvRecord() {
        // "ご利用者","カテゴリ","ご利用日","ご利用先など","ご利用金額(￥)","支払区分","今回回数","訂正サイン","お支払い金額(￥)","国内／海外","摘要","備考"
        ParserSevenCard parser = new ParserSevenCard();
        List<String> list = Lists.newArrayList(
                "ご利用者","カテゴリ","2018/01/23","ご利用先など","1,234","支払区分",
                "今回回数","訂正サイン","2,345","国内／海外","摘要","備考");
        String[] arr = list.toArray(new String[list.size()]);
        CsvRecordSevenCard record = parser.toCsvRecord(arr);
        assertEquals(arr[0], record.getRiyousya());
        assertEquals(arr[1], record.getSyousai1());
        assertEquals(parse("2018/1/23", ofPattern("yyyy/M/d")), record.getRiyoubi());
        assertEquals(arr[3], record.getSyousai2());
        assertEquals(1234L, (long) record.getPrice());
        assertEquals(arr[5], record.getShiharaiKubun());
        assertEquals(arr[6], record.getKaisuu());
        assertEquals(arr[7], record.getTeiseiSign());
        assertEquals(2345L, (long) record.getOshiharaiKingaku());
        assertEquals(arr[9], record.getKokunaiKaigai());
        assertEquals(arr[10], record.getTekiyou());
        assertEquals(arr[11], record.getBikou());
        assertFalse(Strings.isNullOrEmpty(record.getId()));
    }

    @Test
    void testParse() throws Exception {
        ParserSevenCard parser = new ParserSevenCard();
        InputStream is = getClassPathResource("/csv/SevenCard/SevenCard.csv");
        CsvParseResult<CsvRecordSevenCard> res = parser.parseCsv(is);
        assertEquals(1, res.getRecords().size());
        assertTrue(res.getErrors().isEmpty());

        // "****-****-****-1234　セブンカードＶｉｓａ　徳川　家康　様","≪ショッピング取組（国内）≫"," 2018/01/16","ツルハ","1,584","１回","","","1,584","国内","","*"
        CsvRecordSevenCard r1 = res.getRecords().get(7);
        assertEquals("****-****-****-1234　セブンカードＶｉｓａ　徳川　家康　様", r1.getRiyousya());
        assertEquals("≪ショッピング取組（国内）≫", r1.getSyousai1());
        assertEquals(parse("2018/1/16", ofPattern("yyyy/M/d")), r1.getRiyoubi());
        assertEquals("ツルハ", r1.getSyousai2());
        assertEquals(1584L, (long) r1.getPrice());
        assertEquals("１回", r1.getShiharaiKubun());
        assertEquals("", r1.getKaisuu());
        assertEquals("", r1.getTeiseiSign());
        assertEquals(1584L, (long) r1.getOshiharaiKingaku());
        assertEquals("国内", r1.getKokunaiKaigai());
        assertEquals("", r1.getTekiyou());
        assertEquals("*", r1.getBikou());
        assertEquals("20180116-1", r1.getId());
    }
}
