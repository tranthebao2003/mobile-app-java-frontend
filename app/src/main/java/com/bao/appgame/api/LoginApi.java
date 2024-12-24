package com.bao.appgame.api;

import com.bao.appgame.model.EmailRequest;
import com.bao.appgame.model.UserRequest;
import com.bao.appgame.model.UserResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApi {
    @POST("/loginApi")
    Call<UserResponse> getUserInfo(@Body UserRequest userRequest);

    @POST("/registerApi")
    Call<UserResponse> saveRegisterUser(@Body UserResponse userResponse);

    @POST("/forgotPassword")
//    Call<Boolean> sendEmail(@Body String email);
    Call<Boolean> sendEmail(@Body EmailRequest emailPayload);

}

