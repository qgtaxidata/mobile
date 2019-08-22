package com.example.taxidata.net;


import com.example.taxidata.bean.AbnormalInfo;
import com.example.taxidata.bean.AreaAnalyzeInfo;
import com.example.taxidata.bean.AreaIncomeInfo;
import com.example.taxidata.bean.DriverConditionInfo;
import com.example.taxidata.bean.HeatPointInfo;
import com.example.taxidata.bean.HotSpotCallBackInfo;
import com.example.taxidata.bean.HotSpotRouteInfo;
import com.example.taxidata.bean.IncomeRankingInfo;
import com.example.taxidata.bean.RoadQualityInfo;
import com.example.taxidata.bean.TaxiDemandInfo;
import com.example.taxidata.bean.TaxiInfo;
import com.example.taxidata.bean.TaxiPathInfo;
import com.example.taxidata.ui.passengerpath.enity.PathInfo;
import com.example.taxidata.ui.recommendad.AdInfo;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HttpService {

    @POST("thermoDiagram/getAreaMap")
    Call<HeatPointInfo> getHeatPoint(@Query("area")int area,@Query("time")String time);

    @POST("thermoDiagram/getAreaMap")
    Observable<HeatPointInfo> getObservableHeatPoint(@Query("area")int area,@Query("time")String time);

    @POST("thermoDiagram/getFutureMap")
    Observable<HeatPointInfo> getFeatureHeatPoint(@Query("area")int area, @Query("nowTime")String nowTime,
                                            @Query("futureTime")String futureTime, @Query("algorithm")int algorithm);

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("hotspot/findHotspot")
    Observable<HotSpotCallBackInfo>  getHotSpot(@Body RequestBody info);

    /**
     * 路径可视化之查询车牌号
     * @param area
     * @param time
     * @return
     */
    @GET("/taxiRoute/findTaxi")
    Observable<TaxiInfo> getTaxiInfo(@Query("area") int area, @Query("time") String time);

    /**
     * 路径可视化之查询历史路径
     * @param
     * @return
     */
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/taxiRoute/findRoute")
    Observable<TaxiPathInfo> getHistoryTaxiPathInfo(@Body RequestBody info);

    /**
     * 路径可视化之查询实时路径
     * @param
     * @return
     */
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/taxiRoute/findLiveRoute")
    Observable<TaxiPathInfo> getCurrentTaxiPathInfo(@Body RequestBody info);



    @POST("route/getRoute")
    Observable<HotSpotRouteInfo>  getHotSpotRoute(@Query("lonOrigin") double lonOrigin,
                                                  @Query("latOrigin") double latOrigin ,
                                                  @Query("lonDestination") double lonDestination ,
                                                  @Query("latDestination")double latDestination );

    @POST("rank/getRank")
    Observable<IncomeRankingInfo> getIncomeRankingInfo(@Query("area") int area, @Query("date") String date);

    @GET("rank/getSituation")
    Observable<DriverConditionInfo> getDriverConditionInfo(@Query("area") int area, @Query("date") String date, @Query("driverID") String driverID);

    @GET("route/getRoute")
    Observable<PathInfo> getPath(@Query("lonOrigin")double lonOrigin,
                                 @Query("latOrigin")double latOrigin,
                                 @Query("lonDestination")double lonDestination,
                                 @Query("latDestination")double latDestination);

    @GET("AreaRequirement/analyseRequirement")
    Observable<TaxiDemandInfo> getTaxiDemandInfo(@Query("area") int area, @Query("time") String time);

    @GET("/analyse/income")
    Observable<AreaIncomeInfo> getAreaIncomeInfo(@Query("area") int area, @Query("date") String date);

    @GET("analyse/vehicleUtilizationRate")
    Observable<AreaAnalyzeInfo> getAreaAnalyzeInfo(@Query("area") int area, @Query("date") String date);

    @POST("/analyse/roadAnalysis")
    Observable<RoadQualityInfo> getRoadQualityInfo(@Query("area") int area, @Query("date") String date);

    @GET("analyse/billboard")
    Observable<AdInfo> getAdPosition(@Query("area")int area, @Query("targetTime")int targetTime, @Query("targetDay")int targetDay);

    @GET("analyse/abnormalTaxiAnalysis")
    Observable<AbnormalInfo> getAbnormalinfo();
}

