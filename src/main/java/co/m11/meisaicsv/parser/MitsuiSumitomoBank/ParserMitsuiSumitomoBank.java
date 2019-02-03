package co.m11.meisaicsv.parser.MitsuiSumitomoBank;

import co.m11.meisaicsv.common.CsvParser;
import co.m11.meisaicsv.common.CsvRecord;

import java.time.LocalDate;

import static co.m11.meisaicsv.common.CsvType.MitsuiSumitomoBank;
import static co.m11.meisaicsv.common.Util.*;

public class ParserMitsuiSumitomoBank extends CsvParser<CsvRecord> {

    public ParserMitsuiSumitomoBank() {
        super(MitsuiSumitomoBank);
    }

    @Override
    public CsvRecord toCsvRecord(String[] arr) {
        // "年月日（和暦）","お引出し","お預入れ","お取り扱い内容","残高"
        CsvRecord res = new CsvRecord();
        int i = 0;
        res.setRiyoubi(parseJapaneseRiyoubi(arr[i++]).atStartOfDay());
        res.setPrice(parsePrice(arr[i++], arr[i++]));
        res.setSyousai1(arr[i++]);
        res.setBankBalance(parseCommaString(arr[i++]));
        res.setId(md5(res));
        return res;
    }

    public LocalDate parseJapaneseRiyoubi(String s) {
        String[] arr = s.split("\\.");
        String japaneseYear = arr[0];
        int month = Integer.parseInt(arr[1]);
        int day = Integer.parseInt(arr[2]);
        int year;
        if (japaneseYear.startsWith("H")) {
            year = Integer.parseInt(japaneseYear.replaceAll("H", ""));
            year += 1988;
        } else {
            throw new RuntimeException("japanese year can not parse");
            // 新元号対応要
        }
        return LocalDate.now().withYear(year).withMonth(month).withDayOfMonth(day);
    }
}
