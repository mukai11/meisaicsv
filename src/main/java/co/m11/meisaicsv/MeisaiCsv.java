package co.m11.meisaicsv;

import co.m11.meisaicsv.common.CsvParseResult;
import co.m11.meisaicsv.common.CsvParser;
import co.m11.meisaicsv.common.CsvRecord;
import co.m11.meisaicsv.common.MeisaiCsvType;
import com.google.gson.Gson;
import com.opencsv.CSVWriter;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static co.m11.meisaicsv.common.CsvRecord.CSV_HEADER;

public class MeisaiCsv {

    public static String USAGE;
    static {
        List<String> sb = new ArrayList<>();
        sb.add("引数1 : Csv キー。必須。MeisaiCsvType で定義されている。");
        sb.add("引数2 : Csv ファイルパス。必須。");
        sb.add("引数3 : 出力形式。CSV or JSON。デフォルトはCSV。");
        sb.add("");
        sb.add("例");
        sb.add("java -jar meisaicsv.jar SbiNetBank /tmp/sbi.csv");
        USAGE = String.join("\n", sb);
    }

    public static void main(String[] args) throws Exception {
        /**
         * 引数
         */
        if (args.length < 2) {
            System.out.println(USAGE);
            System.exit(1);
        }
        MeisaiCsvType type = MeisaiCsvType.valueOf(args[0]);
        String filePath = args[1];
        String outputType = "";
        if (3 <= args.length) {
            outputType = args[2];
        }
        /**
         * 解析処理
         */
        CsvParseResult res = parse(type, new FileInputStream(new File(filePath)));
        /**
         * 結果出力
         */
        if (StringUtils.equalsAnyIgnoreCase("JSON", outputType)) {
            System.out.println((new Gson()).toJson(res));
        } else {
            System.out.println(toCsv(res));
        }
    }

    public static CsvParseResult parse(MeisaiCsvType type, InputStream inputStream) throws Exception {
        CsvParser parser = (CsvParser) type.getParser().newInstance();
        return parser.parseCsv(inputStream);
    }

    public static String toCsv(CsvParseResult res) {
        try (StringWriter sw = new StringWriter();
             CSVWriter writer = new CSVWriter(sw)) {
            writer.writeNext(CSV_HEADER);
            for (Object record : res.getRecords().values()) {
                writer.writeNext(((CsvRecord)record).toCsv());
            }
            writer.close();
            return sw.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
