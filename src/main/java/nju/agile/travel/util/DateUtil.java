package nju.agile.travel.util;


import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by echo on 2019/1/9.
 */
public class DateUtil {

    private static final String defaultFormatString = "yyyy-MM-dd HH:mm:ss";

    public static String dateToString(Date date){
        return dateToString(date, defaultFormatString);
    }

    public static String dateToString(Date date, String formatString){
        SimpleDateFormat format=new SimpleDateFormat(formatString);
        return format.format(date);
    }

    public static Date stringToDate(String s){
        SimpleDateFormat format=new SimpleDateFormat(defaultFormatString);
        try {
            return new Date(format.parse(s).getTime());
        } catch (ParseException e) {
            throw new RuntimeException("parse ERROR,format="+s);
        }
    }

    public static String TimestampToString(Timestamp timestamp){
        DateFormat dateFormat = new SimpleDateFormat(defaultFormatString);
        return dateFormat.format(timestamp);
    }

    public static Date getCurrentRoundDate(){
        Date now = new Date();
        return DateUtils.round(now, Calendar.SECOND);
    }

    public static String getCurrentRoundDateStr(){
        return dateToString(getCurrentRoundDate());
    }

    public static String getCurrentMillsc(){
        return String.valueOf(new Date().getTime());
    }

}
