package com.example.taxidata.net;


import com.example.taxidata.bean.HeatPointInfo;
import com.example.taxidata.bean.HotSpotCallBackInfo;
import com.example.taxidata.bean.TaxiInfo;

import java.util.ResourceBundle;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
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
    @POST("taxiRoute/findTaxi")
    Observable<TaxiInfo> getTaxiInfo(@Body RequestBody info);

}

