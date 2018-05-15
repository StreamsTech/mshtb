package com.streamstech.droid.mshtb.io;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import com.streamstech.droid.mshtb.data.CellIDResponse;

import java.util.List;

public interface EndpointInterface {


    @POST("register/{firstname}/{lastname}/{mobileno}")
    Call<CommonResponse> register(@Path("firstname") String firstname, @Path("lastname") String lastname,
                                  @Path("mobileno") String mobileno);

    @Multipart
    @POST("upload/{mobileno}/{pin}/{message}")
    Call<CommonResponse> upload(@Path("mobileno") String mobileno, @Path("pin") String pin,
                                  @Path("message") String message,
                                @Part("fileone\"; filename=\"pp.png\" ") RequestBody fileone,
                                @Part("filetwo\"; filename=\"pp.png\" ") RequestBody filetwo);


    @PUT("verify/{mobileno}/{otp}")
    Call<CommonResponse> verify(@Path("mobileno") String mobileno, @Path("otp") String otp);

    @GET("getmedia/{mobileno}/{otp}")
    Call<CommonResponse> getmedia(@Path("mobileno") String mobileno, @Path("otp") String otp);

    @GET("CellIDLocation")
    Call<CellIDResponse> getcellidlocation(@Query("cellid") int cellid, @Query("mcc") String mcc,
                                           @Query("mnc") String mnc, @Query("strength") int signalstrenth,
                                           @Query("lac") int lac);

    @POST("AddCellIDLocation")
    Call<CellIDResponse> addcellidlocation(@Query("longitude") double longitude, @Query("latitude") double latitude,
                                           @Query("cellid") int cellid, @Query("mcc") String mcc,
                                           @Query("mnc") String mnc, @Query("strength") int signalstrenth,
                                           @Query("lac") int lac, @Query("device") String device,
                                           @Query("raw") String raw, @Query("source") String source,
                                           @Query("accuracy") double accuracy, @Query("bearing") float bearing,
                                           @Query("speed") float speed, @Query("network") String network,
                                           @Query("internetprovider") String internetprovider, @Query("operatorname") String operatorname,
                                           @Query("locationname") String locationname, @Query("devicemanufacturer") String devicemanufacturer,
                                           @Query("devicemodel") String devicemodel, @Query("deviceos") String deviceos,
                                           @Query("deviceplatform") String deviceplatform, @Query("country") String country,
                                           @Query("devicetime") String devicetime);
}
