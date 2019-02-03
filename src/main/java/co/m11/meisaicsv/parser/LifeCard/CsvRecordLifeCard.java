package co.m11.meisaicsv.parser.LifeCard;

import co.m11.meisaicsv.common.CsvRecord;

public class CsvRecordLifeCard extends CsvRecord {

    /**
     * 回数
     */
    private String kaisuu;

    /**
     * 利用金額
     */
    private Long riyouKingaku;

    /**
     * ATM利用料
     */
    private Long atmCharge;

    /**
     * 手数料
     */
    private Long tesuuRyou;

    /**
     * 支払回数/何回目
     */
    private String shiharaiKaisuu;

    /**
     * 当月支払金額
     */
    private Long tougetsuShiharaiKingaku;

    /**
     * 支払残高
     */
    private Long shiharaiZandaka;

    public String getKaisuu() {
        return kaisuu;
    }

    public void setKaisuu(String kaisuu) {
        this.kaisuu = kaisuu;
    }

    public Long getRiyouKingaku() {
        return riyouKingaku;
    }

    public void setRiyouKingaku(Long riyouKingaku) {
        this.riyouKingaku = riyouKingaku;
    }

    public Long getAtmCharge() {
        return atmCharge;
    }

    public void setAtmCharge(Long atmCharge) {
        this.atmCharge = atmCharge;
    }

    public Long getTesuuRyou() {
        return tesuuRyou;
    }

    public void setTesuuRyou(Long tesuuRyou) {
        this.tesuuRyou = tesuuRyou;
    }

    public String getShiharaiKaisuu() {
        return shiharaiKaisuu;
    }

    public void setShiharaiKaisuu(String shiharaiKaisuu) {
        this.shiharaiKaisuu = shiharaiKaisuu;
    }

    public Long getTougetsuShiharaiKingaku() {
        return tougetsuShiharaiKingaku;
    }

    public void setTougetsuShiharaiKingaku(Long tougetsuShiharaiKingaku) {
        this.tougetsuShiharaiKingaku = tougetsuShiharaiKingaku;
    }

    public Long getShiharaiZandaka() {
        return shiharaiZandaka;
    }

    public void setShiharaiZandaka(Long shiharaiZandaka) {
        this.shiharaiZandaka = shiharaiZandaka;
    }
}
