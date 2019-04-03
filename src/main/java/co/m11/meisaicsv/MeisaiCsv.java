package co.m11.meisaicsv;

import co.m11.meisaicsv.common.CsvParseResult;
import co.m11.meisaicsv.common.CsvParser;
import co.m11.meisaicsv.common.CsvRecord;
import co.m11.meisaicsv.common.MeisaiCsvType;
import com.google.gson.Gson;
import org.apache.commons.csv.CSVFormat;

import java.io.*;
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
        if ("JSON".equalsIgnoreCase(outputType)) {
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
        try (StringWriter sw = new StringWriter()) {

            CSVFormat.EXCEL.printRecord(sw, (Object[]) CSV_HEADER);

            for (Object record : res.getRecords().values()) {
                CSVFormat.EXCEL.printRecord(sw, (Object[]) ((CsvRecord) record).toCsv());
            }
            return sw.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
