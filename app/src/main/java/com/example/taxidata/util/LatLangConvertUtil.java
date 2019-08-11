package com.example.taxidata.util;

/**
 * @author: ODM
 * @date: 2019/8/11
 */
public class LatLangConvertUtil {

    public static  String  doubleToLatLang(double data) {
        String result = "";
        int  temp_int_du = (int) data;
        result = String.valueOf(temp_int_du) + "°";
        double temp_double_du = data - temp_int_du;
        int temp_fen = (int) (temp_double_du * 60);
        result = result .concat(String.valueOf(temp_fen) +"′");
        double temp_double_fen = temp_double_du * 60 - temp_fen;
        int temp_int_second = (int) (temp_double_fen * 60) ;
        result = result.concat(String.valueOf(temp_int_second) + "″");
        return  result;
    }
}
