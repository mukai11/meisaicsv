package co.m11.meisaicsv.parser.MitsubishiUfjBank;

import co.m11.meisaicsv.common.CsvParser;
import co.m11.meisaicsv.common.IdGenerator;

import static co.m11.meisaicsv.common.MeisaiCsvType.MitsubishiUfjBank;
import static co.m11.meisaicsv.common.MeisaiCsvUtil.parseCommaString;
import static co.m11.meisaicsv.common.MeisaiCsvUtil.parsePrice;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

public class ParserMitsubishiUfjBank extends CsvParser<CsvRecordMitsubishiUfjBank> {

    private IdGenerator idGenerator = new IdGenerator();

    public ParserMitsubishiUfjBank() {
        super(MitsubishiUfjBank);
    }

    @Override
    public CsvRecordMitsubishiUfjBank toCsvRecord(String[] arr) {
        // 日付,摘要,摘要内容,支払い金額,預かり金額,差引残高,メモ,未資金化区分,入払区分
        CsvRecordMitsubishiUfjBank res = new CsvRecordMitsubishiUfjBank();
        int i = 0;
        res.setRiyoubi(parse(arr[i++], ofPattern("yyyy/M/d")));
        res.setSyousai1(arr[i++]);
        res.setSyousai2(arr[i++]);
        res.setPrice(parsePrice(arr[i++], arr[i++]));
        res.setBankBalance(parseCommaString(arr[i++]));
        res.setMemo(arr[i++]);
        res.setMishikinkaKubun(arr[i++]);
        res.setIribaraiKubun(arr[i++]);
        res.setId(idGenerator.getId(res.getRiyoubi()));
        return res;
    }
}
