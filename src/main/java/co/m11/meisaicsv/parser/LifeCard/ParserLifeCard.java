package co.m11.meisaicsv.parser.LifeCard;

import co.m11.meisaicsv.common.CsvParser;

import java.util.List;

import static co.m11.meisaicsv.common.MeisaiCsvType.LifeCard;
import static co.m11.meisaicsv.common.MeisaiCsvUtil.findIndicesFromList;
import static co.m11.meisaicsv.common.MeisaiCsvUtil.parseCommaString;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

public class ParserLifeCard extends CsvParser<CsvRecordLifeCard> {

    public static final String START_REGEXP = "^明細No\\.,契約.*";
    public static final String END_REGEXP = "^回数指定払　お支払（分割支払金）内訳表.*";

    public ParserLifeCard() {
        super(LifeCard);
    }

    @Override
    protected List<String> doBefore(List<String> lines) {
        List<Integer> startIndices = findIndicesFromList(lines, START_REGEXP);
        skipNum = startIndices.get(0) + 1;
        List<Integer> endIndices = findIndicesFromList(lines, END_REGEXP);
        if (! endIndices.isEmpty()) {
            lines = lines.subList(0, endIndices.get(0));
        }
        return lines;
    }

    @Override
    public CsvRecordLifeCard toCsvRecord(String[] arr) {
        // 明細No.,契約,回数,利用日,利用先,利用金額,ATM利用料,手数料,支払総額,支払回数/何回目,当月支払金額,支払残高
        CsvRecordLifeCard res = new CsvRecordLifeCard();
        int i = 0;
        res.setId(arr[i++]);
        res.setSyousai1(arr[i++]);
        res.setKaisuu(arr[i++]);
        res.setRiyoubi(parse(arr[i++], ofPattern("yyyy/MM/dd")));
        res.setSyousai2(arr[i++]);
        res.setRiyouKingaku(parseCommaString(arr[i++]));
        res.setAtmCharge(parseCommaString(arr[i++]));
        res.setTesuuRyou(parseCommaString(arr[i++]));
        res.setPrice(parseCommaString(arr[i++]));
        res.setShiharaiKaisuu(arr[i++]);
        res.setTougetsuShiharaiKingaku(parseCommaString(arr[i++]));
        res.setShiharaiZandaka(parseCommaString(arr[i++]));
        return res;
    }
}
