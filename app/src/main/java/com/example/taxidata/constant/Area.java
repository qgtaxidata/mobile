package com.example.taxidata.constant;

import com.amap.api.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

public class Area {
    private Area(){}

    /**
     * 广州各区域对应的数值
     */
    public static final Map<String,Integer> area = new HashMap<>();

    static {
        area.put("全广州",0);
        area.put("花都区",1);
        area.put("南沙区",2);
        area.put("增城区",3);
        area.put("从化区",4);
        area.put("番禺区",5);
        area.put("白云区",6);
        area.put("黄埔区",7);
        area.put("荔湾区",8);
        area.put("海珠区",9);
        area.put("天河区",10);
        area.put("越秀区",11);
    }

    /**
     * 广州各区域对应的经纬度
     */
    public static final Map<Integer, LatLng> area_latlng = new HashMap<>();

    static {
        //全广州
        area_latlng.put(0,new LatLng(23.132566,113.265531));
        //花都区
        area_latlng.put(1,new LatLng(23.404165,113.220218f));
        //南沙区
        area_latlng.put(2,new LatLng(22.78044,113.43394305));
        //增城区
        area_latlng.put(3,new LatLng(23.261141,113.81086));
        //从化区
        area_latlng.put(4,new LatLng(23.548852,113.586605));
        //番禺区
        area_latlng.put(5,new LatLng(22.937244,113.384129));
        //白云区
        area_latlng.put(6,new LatLng(23.15729,113.273289));
        //黄埔区
        area_latlng.put(7,new LatLng(23.106402,113.459749));
        //荔湾区
        area_latlng.put(8,new LatLng(23.125981,113.244261));
        //海珠区
        area_latlng.put(9,new LatLng(23.083801,113.317388));
        //天河区
        area_latlng.put(10,new LatLng(23.12468,113.3612));
        //越秀区
        area_latlng.put(11,new LatLng(23.128524,113.266841 ));
    }

    public static final String GUANG_ZHOU = "全广州";
    public static final String HUA_DU = "花都区";
    public static final String NAN_SHA = "南沙区";
    public static final String ZENG_CHENG = "增城区";
    public static final String CONG_HUA = "从化区";
    public static final String PAN_YU = "番禺区";
    public static final String BAI_YUN = "白云区";
    public static final String HUANG_PU = "黄埔区";
    public static final String LI_WAN = "荔湾区";
    public static final String HAI_ZHU = "海珠区";
    public static final String TIAN_HE = "天河区";
    public static final String YUE_XIU = "越秀区";
}
