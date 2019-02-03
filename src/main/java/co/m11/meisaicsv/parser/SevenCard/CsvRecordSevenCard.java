package co.m11.meisaicsv.parser.SevenCard;

import co.m11.meisaicsv.common.CsvRecord;

public class CsvRecordSevenCard extends CsvRecord {

    /**
     * ご利用者
     */
    private String riyousya;

    /**
     * 支払区分
     */
    private String shiharaiKubun;

    /**
     * 今回回数
     */
    private String kaisuu;

    /**
     * 訂正サイン
     */
    private String teiseiSign;

    /**
     * お支払い金額(￥)
     */
    private Long oshiharaiKingaku;

    /**
     * 国内／海外
     */
    private String kokunaiKaigai;

    /**
     * 摘要
     */
    private String tekiyou;

    /**
     * 備考
     */
    private String bikou;

    public String getRiyousya() {
        return riyousya;
    }

    public void setRiyousya(String riyousya) {
        this.riyousya = riyousya;
    }

    public String getKaisuu() {
        return kaisuu;
    }

    public void setKaisuu(String kaisuu) {
        this.kaisuu = kaisuu;
    }

    public Long getOshiharaiKingaku() {
        return oshiharaiKingaku;
    }

    public void setOshiharaiKingaku(Long oshiharaiKingaku) {
        this.oshiharaiKingaku = oshiharaiKingaku;
    }

    public String getKokunaiKaigai() {
        return kokunaiKaigai;
    }

    public void setKokunaiKaigai(String kokunaiKaigai) {
        this.kokunaiKaigai = kokunaiKaigai;
    }

    public String getTekiyou() {
        return tekiyou;
    }

    public void setTekiyou(String tekiyou) {
        this.tekiyou = tekiyou;
    }

    public String getBikou() {
        return bikou;
    }

    public void setBikou(String bikou) {
        this.bikou = bikou;
    }

    public String getTeiseiSign() {
        return teiseiSign;
    }

    public void setTeiseiSign(String teiseiSign) {
        this.teiseiSign = teiseiSign;
    }

    public String getShiharaiKubun() {
        return shiharaiKubun;
    }

    public void setShiharaiKubun(String shiharaiKubun) {
        this.shiharaiKubun = shiharaiKubun;
    }
}
