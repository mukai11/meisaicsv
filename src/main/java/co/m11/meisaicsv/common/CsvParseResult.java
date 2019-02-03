package co.m11.meisaicsv.common;

import java.util.LinkedHashMap;
import java.util.Map;

public class CsvParseResult<RECORD extends CsvRecord> {

    /**
     * 明細一覧
     */
    private Map<Integer, RECORD> records = new LinkedHashMap<>();

    /**
     * エラー
     */
    private Map<Integer, Throwable> errors = new LinkedHashMap<>();

    public Map<Integer, RECORD> getRecords() {
        return records;
    }

    public void setRecords(Map<Integer, RECORD> records) {
        this.records = records;
    }

    public Map<Integer, Throwable> getErrors() {
        return errors;
    }

    public void setErrors(Map<Integer, Throwable> errors) {
        this.errors = errors;
    }
}
