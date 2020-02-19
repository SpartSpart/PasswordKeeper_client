package passwordkeeperclient.spart.ru.password_keeper_client.api;

import java.util.Collection;
import java.util.List;

import passwordkeeperclient.spart.ru.password_keeper_client.api.model.DocModel;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.FileModel;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @POST("user/add/")
    Call<Void> addUser(@Body UserModel userModel);

    //@FormUrlEncoded
    @GET("secrets/")
    Call<Collection<SecretModel>> getSecrests(@Header("Authorization") String authHeader);

    @POST("secrets/")
    Call<Long> addSecret(@Header("Authorization") String authHeader, @Body SecretModel secretModel);

    @PUT("secrets/{id}")
    Call<Void> updateSecret(@Header("Authorization") String authHeader, @Path("id") long id, @Body SecretModel secretModel);

    @DELETE("secrets/{id}")
    Call<Void> deleteSecret(@Header("Authorization") String authHeader, @Path("id") long id);

    @PUT("secrets/")
    Call<Void> updateSecrets(@Header("Authorization") String authHeader, @Body List<SecretModel> secretModels);

    @POST("login/")
    Call<Void> loginUser(@Header("Authorization") String authHeader);

    @GET("docs/")
    Call<Collection<DocModel>> getDocs(@Header("Authorization") String authHeader);

    @DELETE("docs/{id}")
    Call<Void> deleteDoc(@Header("Authorization") String authHeader, @Path("id") long id);

    @POST("docs/")
    Call<Long> addDoc(@Header("Authorization") String authHeader, @Body DocModel secretModel);

    @PUT("docs/{id}")
    Call<Void> updateDoc(@Header("Authorization") String authHeader, @Path("id") long id, @Body DocModel docModel);

    @GET("files/info/{id}")
    Call<Collection<FileModel>> getFilesInfo(@Header("Authorization") String authHeader, @Path("id") long id);


}

