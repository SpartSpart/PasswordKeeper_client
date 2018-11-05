package passwordkeeperclient.spart.ru.password_keeper_client.server;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {


    @POST("/add")
    @FormUrlEncoded
    Call<UserModel> addUser(@Field("login") String login,
                            @Field("password") String password,
                            @Field("email") String email);

}

