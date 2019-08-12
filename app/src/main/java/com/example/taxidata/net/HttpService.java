package com.example.taxidata.net;


import com.example.taxidata.bean.HeatPointInfo;
import com.example.taxidata.bean.HotSpotCallBackInfo;
import com.example.taxidata.bean.TaxiInfo;
import com.example.taxidata.bean.TaxiPathInfo;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HttpService {

    @POST("thermoDiagram//getAreaMap")
    Observable<HeatPointInfo> getHeatPoint(@Query("area")int area,@Query("time")String time);

    @POST("thermoDiagram//getFutureMap")
    Observable<HeatPointInfo> getFeatureHeatPoint(@Query("area")int area,@Query("nowTime")String nowTime,
                                           @Query("futureTime")String futureTime,@Query("algorithm")int algorithm);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("hotspot/findHotspot")
    Observable<HotSpotCallBackInfo>  getHotSpot(@Body RequestBody info);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/taxiRoute/findTaxi")
    Observable<TaxiInfo> getTaxiInfo(@Body RequestBody info);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("taxiRoute/findRoute")
    Observable<TaxiPathInfo> getTaxiPathInfo(@Body RequestBody info);

}

