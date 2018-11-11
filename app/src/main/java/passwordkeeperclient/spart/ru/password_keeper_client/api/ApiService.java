package passwordkeeperclient.spart.ru.password_keeper_client.api;

import java.util.Collection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @POST("user/add/")
    Call<Void> addUser(@Body UserModel userModel);

    //@FormUrlEncoded
    @GET("secrets/")
    Call<Collection<SecretModel>> getSecrests(@Header("Authorization") String authHeader);

    @POST("login/")
    Call<Void> loginUser(@Header("Authorization") String authHeader);
}

