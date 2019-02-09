package co.m11.meisaicsv.parser.SevenCard;

import co.m11.meisaicsv.common.CsvParser;

import java.util.List;

import static co.m11.meisaicsv.common.CsvType.SevenCard;
import static co.m11.meisaicsv.common.Util.*;
import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

public class ParserSevenCard extends CsvParser<CsvRecordSevenCard> {

    public ParserSevenCard() {
        super(SevenCard);
    }

    @Override
    protected List<String> doBefore(List<String> lines) {
        String regexp = "^\"ご利用者\",\"カテゴリ\".*";
        List<Integer> indices = findIndicesFromList(lines, regexp);
        if (indices.isEmpty()) {
            throw new RuntimeException("この形式には対応していません。");
        }
        skipNum = indices.get(0) + 1;
        return lines;
    }

    @Override
    public CsvRecordSevenCard toCsvRecord(String[] arr) {
        // "ご利用者","カテゴリ","ご利用日","ご利用先など","ご利用金額(￥)","支払区分","今回回数","訂正サイン","お支払い金額(￥)","国内／海外","摘要","備考"
        CsvRecordSevenCard res = new CsvRecordSevenCard();
        int i = 0;
        res.setRiyousya(arr[i++]);
        res.setSyousai1(arr[i++]);
        res.setRiyoubi(parse(arr[i++].trim(), ofPattern("yyyy/MM/dd")).atStartOfDay());
        res.setSyousai2(arr[i++]);
        res.setPrice(parseCommaString(arr[i++]));
        res.setShiharaiKubun(arr[i++]);
        res.setKaisuu(arr[i++]);
        res.setTeiseiSign(arr[i++]);
        res.setOshiharaiKingaku(parseCommaString(arr[i++]));
        res.setKokunaiKaigai(arr[i++]);
        res.setTekiyou(arr[i++]);
        res.setBikou(arr[i++]);
        res.setId(md5(res));
        return res;
    }
}
