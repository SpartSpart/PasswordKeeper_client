package passwordkeeperclient.spart.ru.password_keeper_client.api;

import passwordkeeperclient.spart.ru.password_keeper_client.api.model.UserModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {


    @FormUrlEncoded
    /*
   @Multipart
   @Headers("Content-Type: application/json")
   */
    @POST("/add")

//    Call<UserModel> addUser(@Body UserModel userModel);
    Call<UserModel> addUser(@Field("login") String login,
                            @Field("password") String password,
                            @Field("email") String email);
}

