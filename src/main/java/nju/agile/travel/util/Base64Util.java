package nju.agile.travel.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Created by echo on 2019/1/9.
 */
public class Base64Util {

    public static String encode(int id, String createdAt) {
        String concat = String.format("%d %s", id, createdAt);
        return Base64.getEncoder().encodeToString(concat.getBytes(StandardCharsets.UTF_8));
    }

    public static String decode(String encodedStr) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedStr);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }
}
