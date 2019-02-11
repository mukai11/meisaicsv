package co.m11.meisaicsv.common;

import com.google.common.base.Strings;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MeisaiCsvUtil {

    public static Long parseCommaString(String s) {
        s = Strings.nullToEmpty(s);
        if (Strings.isNullOrEmpty(s)) {
            return null;
        }
        return Long.parseLong(s.replaceAll(",", ""));
    }

    public static InputStream getClassPathResource(String path){
        return MeisaiCsvUtil.class.getResourceAsStream(path);
    }

    public static long parsePrice(String expense, String income) {
        if (Strings.isNullOrEmpty(income)) {
            return parseCommaString(expense);
        } else {
            return parseCommaString(income) * -1;
        }
    }

    public static List<Integer> findIndicesFromList(List<String> list, String regexp) {
        List<Integer> result = new ArrayList<>();
        for (String s : list) {
            if (Strings.nullToEmpty(s).matches(regexp)) {
                result.add(list.indexOf(s));
            }
        }
        return result;
    }
}
