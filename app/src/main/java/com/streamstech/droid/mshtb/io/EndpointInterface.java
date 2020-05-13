package com.streamstech.droid.mshtb.io;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import com.streamstech.droid.mshtb.data.CellIDResponse;
import com.streamstech.droid.mshtb.data.persistent.Outcome;
import com.streamstech.droid.mshtb.data.persistent.PETAdherence;
import com.streamstech.droid.mshtb.data.persistent.PETAdverseEventFollowup;
import com.streamstech.droid.mshtb.data.persistent.PETClinicianFollowup;
import com.streamstech.droid.mshtb.data.persistent.PETClinicianTBReview;
import com.streamstech.droid.mshtb.data.persistent.PETEligibilitySymptom;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollment;
import com.streamstech.droid.mshtb.data.persistent.PETFollowup;
import com.streamstech.droid.mshtb.data.persistent.PETFollowupEnd;
import com.streamstech.droid.mshtb.data.persistent.PETRegistration;
import com.streamstech.droid.mshtb.data.persistent.PETSymptom;
import com.streamstech.droid.mshtb.data.persistent.PETTestOrder;
import com.streamstech.droid.mshtb.data.persistent.PETTestResult;
import com.streamstech.droid.mshtb.data.persistent.PETTreatmentStart;
import com.streamstech.droid.mshtb.data.persistent.Patient;
import com.streamstech.droid.mshtb.data.persistent.Screening;
import com.streamstech.droid.mshtb.data.persistent.TestIndication;
import com.streamstech.droid.mshtb.data.persistent.TestResultHistopathology;
import com.streamstech.droid.mshtb.data.persistent.TestResultSmear;
import com.streamstech.droid.mshtb.data.persistent.TestResultXPert;
import com.streamstech.droid.mshtb.data.persistent.TestResultXRay;
import com.streamstech.droid.mshtb.data.persistent.Treatment;

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

    @POST("login/{username}/{password}")
    Call<CommonResponse> login(@Path("username") String username, @Path("password") String password);

    @POST("uploadpatient/{token}")
    Call<CommonResponse> uploadpatient(@Path("token") String token, @Body Patient jsonPoints);

    @POST("uploadscreening/{token}")
    Call<CommonResponse> uploadscreening(@Path("token") String token, @Body Screening jsonPoints);

    @POST("uploadtestindication/{token}")
    Call<CommonResponse> uploadtestindication(@Path("token") String token, @Body TestIndication jsonPoints);

    @POST("uploadxrayresult/{token}")
    Call<CommonResponse> uploadxrayresult(@Path("token") String token, @Body TestResultXRay jsonPoints);

    @POST("uploadxpertresult/{token}")
    Call<CommonResponse> uploadxpertresult(@Path("token") String token, @Body TestResultXPert jsonPoints);

    @POST("uploadsmearresult/{token}")
    Call<CommonResponse> uploadsmearresult(@Path("token") String token, @Body TestResultSmear jsonPoints);

    @POST("uploadhistoresult/{token}")
    Call<CommonResponse> uploadhistoresult(@Path("token") String token, @Body TestResultHistopathology jsonPoints);

    @POST("uploadtreatmentinitiation/{token}")
    Call<CommonResponse> uploadtreatmentinitiation(@Path("token") String token, @Body Treatment jsonPoints);

    @POST("uploadtreatmentoutcome/{token}")
    Call<CommonResponse> uploadtreatmentoutcome(@Path("token") String token, @Body Outcome jsonPoints);

    // PET
    @POST("uploadregistration/{token}")
    Call<CommonResponse> uploadregistration(@Path("token") String token, @Body PETRegistration jsonPoints);

    @POST("uploadenrollment/{token}")
    Call<CommonResponse> uploadenrollment(@Path("token") String token, @Body PETEnrollment jsonPoints);

    @POST("uploadsymptom/{token}")
    Call<CommonResponse> uploadsymptom(@Path("token") String token, @Body PETSymptom jsonPoints);

    @POST("uploadtestorder/{token}")
    Call<CommonResponse> uploadtestorder(@Path("token") String token, @Body PETTestOrder jsonPoints);

    @POST("uploadtestresult/{token}")
    Call<CommonResponse> uploadtestresult(@Path("token") String token, @Body PETTestResult jsonPoints);

    @POST("uploadeligibility/{token}")
    Call<CommonResponse> uploadeligibility(@Path("token") String token, @Body PETEligibilitySymptom jsonPoints);

    @POST("uploadtreatmentstart/{token}")
    Call<CommonResponse> uploadtreatmentstart(@Path("token") String token, @Body PETTreatmentStart jsonPoints);

    @POST("uploadadherence/{token}")
    Call<CommonResponse> uploadadherence(@Path("token") String token, @Body PETAdherence jsonPoints);

    @POST("uploadfollowup/{token}")
    Call<CommonResponse> uploadfollowup(@Path("token") String token, @Body PETFollowup jsonPoints);

    @POST("uploadfollowupclinician/{token}")
    Call<CommonResponse> uploadfollowupclinician(@Path("token") String token, @Body PETClinicianFollowup jsonPoints);

    @POST("uploadfollowupcliniciantb/{token}")
    Call<CommonResponse> uploadfollowupcliniciantb(@Path("token") String token, @Body PETClinicianTBReview jsonPoints);

    @POST("uploadadverse/{token}")
    Call<CommonResponse> uploadadverse(@Path("token") String token, @Body PETAdverseEventFollowup jsonPoints);

    @POST("uploadfollowupend/{token}")
    Call<CommonResponse> uploadfollowupend(@Path("token") String token, @Body PETFollowupEnd jsonPoints);

    @GET("syncfastdata/{token}")
    Call<CommonResponse> syncfastdata(@Path("token") String token,  @Query("lastupdatetime") String lastupdatetime);

    @GET("syncfastdata/v2/{token}")
    Call<ResponseBody> syncfastdatav2(@Path("token") String token, @Query("lastupdatetime") String lastupdatetime);

    @GET("syncpetdata/{token}")
    Call<CommonResponse> syncpetdata(@Path("token") String token,  @Query("lastupdatetime") String lastupdatetime);

    @GET("syncpetdata/v2/{token}")
    Call<ResponseBody> syncpetdatav2(@Path("token") String token,  @Query("lastupdatetime") String lastupdatetime);

    @GET("location/{token}")
    Call<CommonResponse> location(@Path("token") String token);

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
