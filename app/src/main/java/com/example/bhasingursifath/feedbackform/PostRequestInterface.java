package com.example.bhasingursifath.feedbackform;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by bhasingursifath on 12-06-2017.
 */

public interface PostRequestInterface {

    @POST("e/1FAIpQLSfCn8YxeEneV5vUKXQfhe2_jEMqr9SwSZ_gGRciij-MQG3oLA/formResponse")
    @FormUrlEncoded
    Call<Void> CompleteForm(

            @Field("entry_1256178576") String Name,
            @Field("entry_426491975") String Gender,
            @Field("entry_2000641666") String Age,
            @Field("entry_1350691592") String NameOfWorkshop,
            @Field("entry_1015570159") String AreaOfWorkshop,
            @Field("entry_721038449") String Feedback

    );
}
