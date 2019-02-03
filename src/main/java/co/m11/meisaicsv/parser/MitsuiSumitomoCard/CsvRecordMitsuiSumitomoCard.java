package co.m11.meisaicsv.parser.MitsuiSumitomoCard;

import co.m11.meisaicsv.common.CsvRecord;

public class CsvRecordMitsuiSumitomoCard extends CsvRecord {

    /**
     * 支払い区分
     */
    private String shiharaiKubun;

    /**
     * 分割回数
     */
    private String bunkatsuKaisu;

    /**
     * お支払い金額
     */
    private Long OshiharaiKingaku;

    public String getShiharaiKubun() {
        return shiharaiKubun;
    }

    public void setShiharaiKubun(String shiharaiKubun) {
        this.shiharaiKubun = shiharaiKubun;
    }

    public String getBunkatsuKaisu() {
        return bunkatsuKaisu;
    }

    public void setBunkatsuKaisu(String bunkatsuKaisu) {
        this.bunkatsuKaisu = bunkatsuKaisu;
    }

    public Long getOshiharaiKingaku() {
        return OshiharaiKingaku;
    }

    public void setOshiharaiKingaku(Long oshiharaiKingaku) {
        OshiharaiKingaku = oshiharaiKingaku;
    }
}
