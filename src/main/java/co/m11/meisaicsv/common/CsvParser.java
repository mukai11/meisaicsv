package co.m11.meisaicsv.common;

import com.opencsv.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

import static org.apache.commons.io.IOUtils.readLines;

public abstract class CsvParser<RECORD extends CsvRecord> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    protected CsvType type;
    protected int skipNum = 1;

    protected CsvParser(CsvType type) {
        this.type = type;
    }

    public CsvParseResult<RECORD> parseCsv(InputStream inputStream) throws Exception {
        CsvParseResult<RECORD> result = generateCsvParseResult();
        List<String> lines = readLines(inputStream, type.getEncode());
        lines = doBefore(result, lines);
        List<String[]> list = parseAsStringList(lines);
        for (int i = 0; i < list.size(); i++) {
            String[] arr = list.get(i);
            try {
                RECORD record = toCsvRecord(arr);
                if (record != null) {
                    result.getRecords().put(getLineNum(i), record);
                }
            } catch (Throwable t) {
                logger.debug(t.getMessage() + " at linenum:" + getLineNum(i));
                result.getErrors().put(getLineNum(i), t);
            }
        }
        doAfter(result, lines);
        return result;
    }

    protected int getLineNum(int index) {
        return index + getSkipNum() + 1;
    }

    protected List<String[]> parseAsStringList(List<String> lines) throws Exception {
        return new CSVReaderBuilder(new StringReader(String.join("\n", lines))).withSkipLines(getSkipNum()).build().readAll();
    }

    protected CsvParseResult<RECORD> generateCsvParseResult() {
        return new CsvParseResult<>();
    }

    protected int getSkipNum() {
        return skipNum;
    }

    protected List<String> doBefore(CsvParseResult<RECORD> result, List<String> lines) {
        return lines;
    }

    protected void doAfter(CsvParseResult<RECORD> result, List<String> lines) {
    }

    protected abstract RECORD toCsvRecord(String[] arr);
}
