package co.m11.meisaicsv.parser.LifeCard;

import co.m11.meisaicsv.common.CsvParseResult;
import co.m11.meisaicsv.common.Util;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.jupiter.api.Assertions.*;

public class TestParserLifeCard {

    @Test
    void testToCsvRecord() {
        // 明細No.,契約,回数,利用日,利用先,利用金額,ATM利用料,手数料,支払総額,支払回数/何回目,当月支払金額,支払残高
        ParserLifeCard parser = new ParserLifeCard();
        List<String> list = Lists.newArrayList("明細No.", "契約", "回数", "2018/01/23",
                "利用先", "123", "234", "345", "456", "支払回数/何回目", "567", "678");

        String[] arr = list.toArray(new String[list.size()]);
        CsvRecordLifeCard record = parser.toCsvRecord(arr);
        assertEquals(arr[0], record.getId());
        assertEquals(arr[1], record.getSyousai1());
        assertEquals(arr[2], record.getKaisuu());
        assertEquals(parse("2018/1/23", ofPattern("yyyy/M/d")), record.getRiyoubi());
        assertEquals(arr[4], record.getSyousai2());
        assertEquals(123L, (long) record.getRiyouKingaku());
        assertEquals(234L, (long) record.getAtmCharge());
        assertEquals(345L, (long) record.getTesuuRyou());
        assertEquals(456L, (long) record.getPrice());
        assertEquals(arr[9], record.getShiharaiKaisuu());
        assertEquals(567L, (long) record.getTougetsuShiharaiKingaku());
        assertEquals(678L, (long) record.getShiharaiZandaka());
    }

    @Test
    void testParse() throws Exception {
        ParserLifeCard parser = new ParserLifeCard();
        InputStream is = Util.getClassPathResource("/csv/LifeCard/LifeCard.csv");
        CsvParseResult<CsvRecordLifeCard> res = parser.parseCsv(is);
        assertEquals(2, res.getRecords().size());
        assertTrue(res.getErrors().isEmpty());

        // 1113,ショッピング,1回,2017/11/27,東京ガス　１７年１１月ガス料金,4877,,0,4877,1回払,4877,4877
        CsvRecordLifeCard r1 = res.getRecords().get(21);
        assertEquals("1113", r1.getId());
        assertEquals("ショッピング", r1.getSyousai1());
        assertEquals("1回", r1.getKaisuu());
        assertEquals(parse("2017/11/27", ofPattern("yyyy/M/d")), r1.getRiyoubi());
        assertEquals("東京ガス　１７年１１月ガス料金", r1.getSyousai2());
        assertEquals(4877L, (long) r1.getRiyouKingaku());
        assertNull(r1.getAtmCharge());
        assertEquals(0L, (long) r1.getTesuuRyou());
        assertEquals(4877L, (long) r1.getPrice());
        assertEquals("1回払", r1.getShiharaiKaisuu());
        assertEquals(4877L, (long) r1.getTougetsuShiharaiKingaku());
        assertEquals(4877L, (long) r1.getShiharaiZandaka());

        // 1114,ショッピング,1回,2017/12/24,ＮＴＴヒガシニホン１２ガツブン,2970,,0,2970,1回払,2970,2970
        CsvRecordLifeCard r2 = res.getRecords().get(22);
        assertEquals("1114", r2.getId());
        assertEquals("ショッピング", r2.getSyousai1());
        assertEquals("1回", r2.getKaisuu());
        assertEquals(parse("2017/12/24", ofPattern("yyyy/M/d")), r2.getRiyoubi());
        assertEquals("ＮＴＴヒガシニホン１２ガツブン", r2.getSyousai2());
        assertEquals(2970L, (long) r2.getRiyouKingaku());
        assertNull(r2.getAtmCharge());
        assertEquals(0L, (long) r2.getTesuuRyou());
        assertEquals(2970L, (long) r2.getPrice());
        assertEquals("1回払", r2.getShiharaiKaisuu());
        assertEquals(2970L, (long) r2.getTougetsuShiharaiKingaku());
        assertEquals(2970L, (long) r2.getShiharaiZandaka());
    }
}
