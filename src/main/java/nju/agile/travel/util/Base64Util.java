package nju.agile.travel.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Created by echo on 2019/1/9.
 */
public class Base64Util {
    public static String encode(int id, String createdAt){
        String concat = String.format("%d %s", id, createdAt);
        try {
            return Base64.getEncoder().encodeToString(concat.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return concat;
    }

    public static String decode(String encodedStr){
        byte[] decodedBytes = Base64.getDecoder().decode(encodedStr);
        String decodedStr = "";
        try {
            decodedStr = new String(decodedBytes, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return decodedStr;
    }
}
