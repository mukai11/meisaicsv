package co.m11.meisaicsv.common;

public enum MeisaiCsvType {

    /**
     * こちらで管理しています
     * https://docs.google.com/spreadsheets/d/1021phhLNDIy9s7eMswZIpa_rFGjDZUDvNZFGRp4-1HA
     */

    MitsubishiUfjBank("株式会社三菱ＵＦＪ銀行","銀行","SJIS","","2019/1/21","2019/1/21"),
    MitsuiSumitomoBank("株式会社三井住友銀行","銀行","SJIS","","2019/1/21","2019/1/21"),
    YuuchoBank("株式会社ゆうちょ銀行","銀行","SJIS","","2019/1/21","2019/1/21"),
    SevenCard("株式会社セブン・カードサービス","貸金","SJIS","","2019/1/23","2019/1/23"),
    LifeCard("ライフカード株式会社","貸金","SJIS","","2019/1/24","2019/1/24"),
    MitsuiSumitomoCard("三井住友カード株式会社","貸金","MS932","","2019/1/25","2019/1/25"),
    SbiNetBank("住信SBIネット銀行株式会社","貸金","SJIS","","2019/2/10","1899/12/30"),
    ;

    private String name;
    private String key;
    private String encode;
    private String description;
    private String updateDate;
    private String createDate;

    MeisaiCsvType(String name, String key, String encode, String description, String updateDate, String createDate) {
        this.name = name;
        this.key = key;
        this.encode = encode;
        this.description = description;
        this.updateDate = updateDate;
        this.createDate = createDate;
    }

    public Class getParser() throws ClassNotFoundException {
        return getClass().forName("co.m11.meisaicsv.parser." + toString() + ".Parser" + toString());
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getKey() {
        return key;
    }

    public String getEncode() {
        return encode;
    }
}
