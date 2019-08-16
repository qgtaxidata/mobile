package com.example.taxidata.net;


import com.example.taxidata.bean.AreaAnalyzeInfo;
import com.example.taxidata.bean.AreaIncomeInfo;
import com.example.taxidata.bean.CurrentTaxiPathInfo;
import com.example.taxidata.bean.DriverConditionInfo;
import com.example.taxidata.bean.HeatPointInfo;
import com.example.taxidata.bean.HotSpotCallBackInfo;
import com.example.taxidata.bean.HotSpotRouteInfo;
import com.example.taxidata.bean.IncomeRankingInfo;
import com.example.taxidata.bean.RoadQualityInfo;
import com.example.taxidata.bean.TaxiDemandInfo;
import com.example.taxidata.bean.TaxiInfo;
import com.example.taxidata.bean.TaxiPathInfo;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HttpService {

    @POST("thermoDiagram/getAreaMap")
    Observable<HeatPointInfo> getHeatPoint(@Query("area")int area,@Query("time")String time);

    @POST("thermoDiagram/getFutureMap")
    Observable<HeatPointInfo> getFeatureHeatPoint(@Query("area")int area,@Query("nowTime")String nowTime,
                                           @Query("futureTime")String futureTime,@Query("algorithm")int algorithm);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("hotspot/findHotspot")
    Observable<HotSpotCallBackInfo>  getHotSpot(@Body RequestBody info);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/taxiRoute/findTaxi")
    Observable<TaxiInfo> getTaxiInfo(@Body RequestBody info);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/taxiRoute/findRoute")
    Observable<TaxiPathInfo> getHistoryTaxiPathInfo(@Body RequestBody info);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/taxiRoute/findLiveRoute")
    Observable<CurrentTaxiPathInfo> getCurrentTaxiPathInfo(@Body RequestBody info);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/")
    Observable<HotSpotRouteInfo>  getHotSpotRoute(@Body RequestBody info);

    @POST("rank/getRank")
    Observable<IncomeRankingInfo> getIncomeRankingInfo(@Query("area") int area, @Query("date") String date);

    @GET("rank/getSituation")
    Observable<DriverConditionInfo> getDriverConditionInfo(@Query("area") int area, @Query("date") String date, @Query("driverID") String driverID);

    @GET("AreaRequirement/analyseRequirement")
    Observable<TaxiDemandInfo> getTaxiDemandInfo(@Query("area") int area, @Query("time") String time);

    @POST("")
    Observable<AreaIncomeInfo> getAreaIncomeInfo(@Query("area") int area, @Query("date") String date);

    @POST("")
    Observable<AreaAnalyzeInfo> getAreaAnalyzeInfo(@Query("area") int area, @Query("date") String date);

    @POST("")
    Observable<RoadQualityInfo> getRoadQualityInfo(@Query("area") int area, @Query("date") String date);


}

