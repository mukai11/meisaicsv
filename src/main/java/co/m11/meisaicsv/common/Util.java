package co.m11.meisaicsv.common;

import com.google.common.base.Strings;
import com.google.gson.Gson;

import javax.xml.bind.DatatypeConverter;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class Util {

    public static String md5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(Strings.nullToEmpty(s).getBytes());
            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String md5(Object o) {
        return md5((new Gson()).toJson(o));
    }

    public static Long parseCommaString(String s) {
        s = Strings.nullToEmpty(s);
        if (Strings.isNullOrEmpty(s)) {
            return null;
        }
        return Long.parseLong(s.replaceAll(",", ""));
    }

    public static InputStream getClassPathResource(String path){
        return Util.class.getResourceAsStream(path);
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
