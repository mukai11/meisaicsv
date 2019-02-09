package co.m11.meisaicsv.parser.MitsuiSumitomoCard;

import co.m11.meisaicsv.common.CsvParser;
import co.m11.meisaicsv.common.IdGenerator;

import java.util.List;
import java.util.stream.Collectors;

import static co.m11.meisaicsv.common.CsvType.MitsuiSumitomoCard;
import static co.m11.meisaicsv.common.Util.parseCommaString;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

public class ParserMitsuiSumitomoCard extends CsvParser<CsvRecordMitsuiSumitomoCard> {

    private IdGenerator idGenerator = new IdGenerator();

    public ParserMitsuiSumitomoCard() {
        super(MitsuiSumitomoCard);
    }

    @Override
    protected List<String> doBefore(List<String> lines) {
        return lines.stream().filter(l -> ! l.startsWith(",")).collect(Collectors.toList());
    }

    @Override
    public CsvRecordMitsuiSumitomoCard toCsvRecord(String[] arr) {
        // ご利用日,ご利用店名,ご利用金額,支払い区分,分割回数,お支払い金額,備考
        CsvRecordMitsuiSumitomoCard res = new CsvRecordMitsuiSumitomoCard();
        int i = 0;
        res.setRiyoubi(parse(arr[i++], ofPattern("yyyy/MM/dd")));
        res.setSyousai1(arr[i++]);
        res.setPrice(parseCommaString(arr[i++]));
        res.setShiharaiKubun(arr[i++]);
        res.setBunkatsuKaisu(arr[i++]);
        res.setOshiharaiKingaku(parseCommaString(arr[i++]));
        res.setSyousai2(arr[i++]);
        res.setId(idGenerator.getId(res.getRiyoubi()));
        return res;
    }
}
