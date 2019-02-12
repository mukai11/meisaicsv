package co.m11.meisaicsv.common;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CsvParseResult<RECORD extends CsvRecord> {

    /**
     * Csv 解析前
     */
    private List<String> lines;

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

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }
}
