package com.example.taxidata.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeChangeUtil {

    public static String transform(String time) throws ParseException {
        SimpleDateFormat beforeFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date transformDate = beforeFormat.parse(time);
        DateFormat afterFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = afterFormat.format(transformDate);
        return date;
    }

    public static String transformToString(Long milliseconds)throws ParseException{
        Date date = new Date(Long.parseLong(milliseconds+""));
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
