package co.m11.meisaicsv.parser.MitsuiSumitomoCard;

import co.m11.meisaicsv.common.CsvParseResult;
import co.m11.meisaicsv.common.CsvParser;

import java.util.List;
import java.util.stream.Collectors;

import static co.m11.meisaicsv.common.CsvType.MitsuiSumitomoCard;
import static co.m11.meisaicsv.common.Util.md5;
import static co.m11.meisaicsv.common.Util.parseCommaString;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

public class ParserMitsuiSumitomoCard extends CsvParser<CsvRecordMitsuiSumitomoCard> {

    public ParserMitsuiSumitomoCard() {
        super(MitsuiSumitomoCard);
    }

    @Override
    protected List<String> doBefore(CsvParseResult<CsvRecordMitsuiSumitomoCard> result, List<String> lines) {
        return lines.stream().filter(l -> ! l.startsWith(",")).collect(Collectors.toList());
    }

    @Override
    public CsvRecordMitsuiSumitomoCard toCsvRecord(String[] arr) {
        // ご利用日,ご利用店名,ご利用金額,支払い区分,分割回数,お支払い金額,備考
        CsvRecordMitsuiSumitomoCard res = new CsvRecordMitsuiSumitomoCard();
        int i = 0;
        res.setRiyoubi(parse(arr[i++], ofPattern("yyyy/MM/dd")).atStartOfDay());
        res.setSyousai1(arr[i++]);
        res.setPrice(parseCommaString(arr[i++]));
        res.setShiharaiKubun(arr[i++]);
        res.setBunkatsuKaisu(arr[i++]);
        res.setOshiharaiKingaku(parseCommaString(arr[i++]));
        res.setSyousai2(arr[i++]);
        res.setId(md5(res));
        return res;
    }
}
