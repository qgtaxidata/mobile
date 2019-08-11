package com.example.taxidata.net;


import com.example.taxidata.bean.HeatPointInfo;
import com.example.taxidata.bean.HotSpotCallBackInfo;
import com.example.taxidata.bean.TaxiInfo;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface HttpService {

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("thermoDiagram/getMap")
    Observable<HeatPointInfo> getHeatPoint(@Body RequestBody info);


    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("hotspot/findHotspot")
    Observable<HotSpotCallBackInfo>  getHotSpot(@Body RequestBody info);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/taxiRoute/findTaxi")
    Observable<TaxiInfo> getTaxiInfo(@Body RequestBody info);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/findTaxi/findRoute")
    Observable<TaxiInfo> getTaxiPathInfo(@Body RequestBody info);

}

