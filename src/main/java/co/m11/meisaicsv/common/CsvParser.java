package co.m11.meisaicsv.common;

import com.opencsv.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

import static org.apache.commons.io.IOUtils.readLines;

/**
 * 解析基底クラス
 * @param <RECORD>
 */
public abstract class CsvParser<RECORD extends CsvRecord> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    protected MeisaiCsvType type;
    /**
     * 読み飛ばし行数
     */
    protected int skipNum = 1;
    /**
     * 読み取り結果。明細のリストやエラーを持っている。
     */
    protected CsvParseResult<RECORD> result;

    /**
     * MeisaiCsvType 必須
     * @param type
     */
    protected CsvParser(MeisaiCsvType type) {
        this.type = type;
    }

    /**
     * Csv 解析
     * @param inputStream
     * @return CsvParseResult
     * @throws Exception
     */
    public CsvParseResult<RECORD> parseCsv(InputStream inputStream) throws Exception {
        /**
         * inputStream をString 型で読み出し
         */
        List<String> lines = readLines(inputStream, type.getEncode());
        return parseCsv(lines);
    }

    public CsvParseResult<RECORD> parseCsv(List<String> lines) throws Exception {
        /**
         * 結果用オブジェクトを作成
         */
        result = generateCsvParseResult();
        result.setLines(lines);
        /**
         * before イベント
         */
        lines = doBefore(lines);
        /**
         * Csv 解析(thanks to OpenCSV)
         */
        List<String[]> list = parseAsStringList(lines);
        /**
         * 明細毎にループ
         */
        for (int i = 0; i < list.size(); i++) {
            String[] arr = list.get(i);
            try {
                /**
                 * RECORD 型に変換
                 */
                RECORD record = toCsvRecord(arr);
                /**
                 * null の場合は無効なレコードとみなして溜め込まない
                 */
                if (record != null) {
                    result.getRecords().put(getLineNum(i), record);
                }
            } catch (Throwable t) {
                /**
                 * 例外が起きたら、errors に溜め込む
                 */
                logger.debug(t.getMessage() + " at linenum:" + getLineNum(i));
                result.getErrors().put(getLineNum(i), t);
            }
        }
        /**
         * after イベント
         */
        doAfter(lines);
        return result;
    }

    protected int getLineNum(int index) {
        return index + getSkipNum() + 1;
    }

    protected List<String[]> parseAsStringList(List<String> lines) throws Exception {
        return new CSVReaderBuilder(new StringReader(String.join("\n", lines)))
                .withSkipLines(getSkipNum()).build().readAll();
    }

    protected CsvParseResult<RECORD> generateCsvParseResult() {
        return new CsvParseResult<>();
    }

    protected int getSkipNum() {
        return skipNum;
    }

    /**
     * 読み込み前イベント。主に読み飛ばす行を指定する際に使う。
     * 明細より上部に読み飛ばし行がある場合は、skipNum(1始まり) を指定する。
     * 明細より下部に読み飛ばし行がある場合は、除外したlines を返す。
     * @param lines Csv ファイルの全ての行
     * @return
     */
    protected List<String> doBefore(List<String> lines) {
        return lines;
    }

    /**
     * 読み込み後イベント。
     * @param lines
     */
    protected void doAfter(List<String> lines) {
    }

    /**
     * String[] 型のCsv 1明細から、RECORD 型に変換する。
     * @param arr
     * @return
     */
    protected abstract RECORD toCsvRecord(String[] arr);
}
