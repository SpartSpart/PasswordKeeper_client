package passwordkeeperclient.spart.ru.password_keeper_client.requests.files;

import android.os.AsyncTask;

import android.util.Base64;


import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ServerConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.FileModel;
import passwordkeeperclient.spart.ru.password_keeper_client.credentianals.Principal;
import retrofit2.Call;
import retrofit2.Response;

public class UploadFile extends AsyncTask<Void, Void, FileModel> {
    private String sessionId;
    private long doc_id;
    private File file;

    public UploadFile(long doc_id, File file) {
        this.sessionId = Principal.getSessionId();
        this.doc_id = doc_id;
        this.file = file;
    }

    @Override
    protected FileModel doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Call<FileModel> call = apiService.uploadFile(sessionId,doc_id,body);
        try {
            Response<FileModel> response = call.execute();
            if (response.isSuccessful())
                return response.body();

        } catch (IOException e) {
            ServerConnection.setIOException(true);
            return null;
        }
        return null;
    }


}
