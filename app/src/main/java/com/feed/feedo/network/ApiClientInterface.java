package com.feed.feedo.network;

import com.feed.feedo.model.BackgroundImageModel;
import com.feed.feedo.model.CustumerExist;
import com.feed.feedo.model.LoginModel;
import com.feed.feedo.model.LogoutModel;
import com.feed.feedo.model.ShivamSirQuestionModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiClientInterface {


    @Headers("Content-type: application/json")
    @GET("getResources.aspx")
    public Call<BackgroundImageModel> getBackgroundImage(@Query("clientId") int clientId);

    @Headers("Content-type: application/json")
    @GET("login.aspx")
    public Call<LoginModel> login(@Query("user") String user, @Query("password") String password , @Query("deviceId") String deviceId);


//    @Headers("Content-type: application/json")
//    @GET("Register/VerifyOTP")
//    public Call<LoginOtpModel> verifyOtp(@Query("MobileNo") String mobileNumber, @Query("OTP") String otp);
//
//    @POST("Register/RegisterUser")
//    Call<SignUpActivityModel> signUp(@Body SignUpModelRetrofit retrofit);
//
//
//    @POST("Register/Login")
//    Call<SignInModel> signIn(@Body SignInSendModel retrofit);
//
//
//    @Headers("Content-type: application/json")
//    @GET("Complaint/GetAmenities")
//    public Call<EssentialInfoMasterModel> getAmenities(@Query("Type") String type);



    @Headers("Content-type: application/json")
    @GET("logout.aspx")
    public Call<LogoutModel> logout(@Query("user") String user, @Query("password") String password);


    @Headers("Content-type: application/json")
    @GET("getQuestions.aspx")
    public Call<ShivamSirQuestionModel> getQuiz(@Query("clientId") int clientId);


    @Headers("Content-type: application/json")
    @GET("submitFeedback.aspx")
    public Call<ShivamSirQuestionModel> submitFeedback(@Query("name") String name,
                                                       @Query("mobile") String mobile,
                                                       @Query("gender") String gender,
                                                       @Query("DOB") String dob,
                                                       @Query("DOA") String doa,
                                                       @Query("quesIds") String quesIds,
                                                       @Query("answers") String answers,
                                                       @Query("deviceId") String deviceId,
                                                       @Query("userId") int userId,
                                                       @Query("clientid") int clientid);


    @Headers("Content-type: application/json")
    @GET("isCustomerExist.aspx")
    public Call<CustumerExist> isCustomerExist(@Query("mobile") String mobile);
}