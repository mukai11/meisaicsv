package co.m11.meisaicsv.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.*;

/**
 * 明細レコード
 */
public class CsvRecord {

    /**
     * 明細を識別するID
     * csv 上に存在しない場合はid 以外の情報のmd5
     */
	private String id;

    /**
     * 利用日
     */
    private LocalDate riyoubi;

    /**
     * 出金額、入金はマイナスで表現
     */
    private Long price;

    /**
     * 詳細1、摘要と呼ぶこともある
     */
    private String syousai1;

    /**
     * 詳細2、摘要内容と呼ぶこともる
     */
    private String syousai2;

    /**
     * メモ
     */
    private String memo;

    /**
     * 残高（銀行CSVの場合のみ）
     */
    private Long bankBalance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getRiyoubi() {
        return riyoubi;
    }

    public void setRiyoubi(LocalDate riyoubi) {
        this.riyoubi = riyoubi;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getSyousai1() {
        return syousai1;
    }

    public void setSyousai1(String syousai1) {
        this.syousai1 = syousai1;
    }

    public String getSyousai2() {
        return syousai2;
    }

    public void setSyousai2(String syousai2) {
        this.syousai2 = syousai2;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(Long bankBalance) {
        this.bankBalance = bankBalance;
    }

    public String[] toCsv() {
        List<String> items = new ArrayList<>();
        items.add(getId());
        items.add(getRiyoubi().format(ofPattern("yyyy/MM/dd")));
        items.add(getSyousai1());
        items.add(getSyousai2());
        items.add(getPrice() + "");
        items.add(getMemo());
        items.add(getBankBalance() + "");
        return items.toArray(new String[items.size()]);
    }
}
