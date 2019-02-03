package co.m11.meisaicsv.parser.YuuchoBank;

import co.m11.meisaicsv.common.CsvParseResult;
import co.m11.meisaicsv.common.CsvParser;
import co.m11.meisaicsv.common.CsvRecord;

import java.util.List;

import static co.m11.meisaicsv.common.CsvType.YuuchoBank;
import static co.m11.meisaicsv.common.Util.*;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

public class ParserYuuchoBank extends CsvParser<CsvRecord> {

    public static final String START_REGEXP = "^取引日,入出金明細ＩＤ.*";

    public ParserYuuchoBank() {
        super(YuuchoBank);
    }

    @Override
    protected List<String> doBefore(CsvParseResult<CsvRecord> result, List<String> lines) {
        List<Integer> startIndices = findIndicesFromList(lines, START_REGEXP);
        skipNum = startIndices.get(0) + 1;
        return lines;
    }

    @Override
    public CsvRecord toCsvRecord(String[] arr) {
        // 取引日,入出金明細ＩＤ,受入金額（円）,払出金額（円）,詳細１,詳細２,現在（貸付）高,
        CsvRecord res = new CsvRecord();
        int i = 0;
        res.setRiyoubi(parse(arr[i++], ofPattern("yyyyMMdd")).atStartOfDay());
        res.setId(arr[i++]);
        String income = arr[i++];
        res.setPrice(parsePrice(arr[i++], income));
        res.setSyousai1(arr[i++]);
        res.setSyousai2(arr[i++]);
        res.setBankBalance(parseCommaString(arr[i++]));
        return res;
    }
}
