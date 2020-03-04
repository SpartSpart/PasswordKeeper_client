package passwordkeeperclient.spart.ru.password_keeper_client.api;

import java.util.Collection;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.DocModel;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.FileModel;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.UserModel;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

public interface ApiService {

    @POST("user/add/")
    Call<Void> addUser(@Body UserModel userModel);

    //@FormUrlEncoded
    @GET("secrets/")
    Call<Collection<SecretModel>> getSecrests(@Header("Cookie") String authHeader);

    @POST("secrets/")
    Call<Long> addSecret(@Header("Cookie") String authHeader, @Body SecretModel secretModel);

    @PUT("secrets/{id}")
    Call<Void> updateSecret(@Header("Cookie") String authHeader, @Path("id") long id, @Body SecretModel secretModel);

    @DELETE("secrets/{id}")
    Call<Void> deleteSecret(@Header("Cookie") String authHeader, @Path("id") long id);

    @PUT("secrets/")
    Call<Void> updateSecrets(@Header("Cookie") String authHeader, @Body List<SecretModel> secretModels);

    @POST("login/")
    Call<Void> loginUser(@Header("Authorization") String authHeader);

    @GET("docs/")
    Call<Collection<DocModel>> getDocs(@Header("Cookie") String authHeader);

    @DELETE("docs/{id}")
    Call<Void> deleteDoc(@Header("Cookie") String authHeader, @Path("id") long id);

    @POST("docs/")
    Call<Long> addDoc(@Header("Cookie") String authHeader, @Body DocModel secretModel);

    @PUT("docs/{id}")
    Call<Void> updateDoc(@Header("Cookie") String authHeader, @Path("id") long id, @Body DocModel docModel);

    @GET("files/info/{id}")
    Call<Collection<FileModel>> getFilesInfo(@Header("Cookie") String authHeader, @Path("id") long id);

    @POST("files/delete/")
    Call<Void> deleteFiles(@Header("Cookie") String authHeader, @Body List<Long> idFileList);

    @GET("files/{id}")
    @Streaming
    Call<ResponseBody> getFile(@Header("Cookie") String authHeader, @Path("id") long id);

    @Multipart
    @POST("upload/file/{doc_id}")
    Call<FileModel> uploadFile(@Header("Cookie") String authHeader,
                              @Path("doc_id") long id,
                              @Part MultipartBody.Part file);

    @POST("files/update/{file_id}")
    Call<Boolean> updateFileName(@Header("Cookie") String authHeader, @Path("file_id") long file_id, @Body String newFileName);
}

