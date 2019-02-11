package co.m11.meisaicsv.parser.SbiNetBank;

import co.m11.meisaicsv.common.CsvParser;
import co.m11.meisaicsv.common.CsvRecord;
import co.m11.meisaicsv.common.IdGenerator;

import static co.m11.meisaicsv.common.MeisaiCsvType.SbiNetBank;
import static co.m11.meisaicsv.common.MeisaiCsvUtil.parseCommaString;
import static co.m11.meisaicsv.common.MeisaiCsvUtil.parsePrice;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

public class ParserSbiNetBank extends CsvParser<CsvRecord> {

    private IdGenerator idGenerator = new IdGenerator();

    public ParserSbiNetBank() {
        super(SbiNetBank);
    }

    @Override
    public CsvRecord toCsvRecord(String[] arr) {
        // "日付","内容","出金金額(円)","入金金額(円)","残高(円)","メモ"
        CsvRecord res = new CsvRecord();
        int i = 0;
        res.setRiyoubi(parse(arr[i++], ofPattern("yyyy/MM/dd")));
        res.setSyousai1(arr[i++]);
        String expense = arr[i++];
        res.setPrice(parsePrice(expense, arr[i++]));
        res.setBankBalance(parseCommaString(arr[i++]));
        res.setMemo(arr[i++]);
        res.setId(idGenerator.getId(res.getRiyoubi()));
        return res;
    }
}
