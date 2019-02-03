package co.m11.meisaicsv.parser.MitsubishiUfjBank;

import co.m11.meisaicsv.common.CsvRecord;

public class CsvRecordMitsubishiUfjBank extends CsvRecord {

    /**
     * 未資金化区分
     */
    private String mishikinkaKubun;

    /**
     * 入払区分
     */
    private String iribaraiKubun;

    public String getMishikinkaKubun() {
        return mishikinkaKubun;
    }

    public void setMishikinkaKubun(String mishikinkaKubun) {
        this.mishikinkaKubun = mishikinkaKubun;
    }

    public String getIribaraiKubun() {
        return iribaraiKubun;
    }

    public void setIribaraiKubun(String iribaraiKubun) {
        this.iribaraiKubun = iribaraiKubun;
    }
}
