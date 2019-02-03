package co.m11.meisaicsv;

import co.m11.meisaicsv.common.CsvParseResult;
import co.m11.meisaicsv.common.CsvParser;
import co.m11.meisaicsv.common.CsvType;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class MeisaiCsv {

    public static void main(String[] args) throws Exception {
        int i = 0;
        String typeName = args[i++];
        String filePath = args[i++];
        CsvType type = CsvType.valueOf(typeName);
        CsvParseResult res = parse(type, new FileInputStream(new File(filePath)));
        System.out.println((new Gson()).toJson(res));
    }

    public static CsvParseResult parse(CsvType type, InputStream inputStream) throws Exception {
        CsvParser parser = (CsvParser) type.getParser().newInstance();
        return parser.parseCsv(inputStream);
    }
}
