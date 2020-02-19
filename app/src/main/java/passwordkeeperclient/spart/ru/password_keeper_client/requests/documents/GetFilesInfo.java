package passwordkeeperclient.spart.ru.password_keeper_client.requests.documents;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;
import java.util.Collection;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ServerConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.DocModel;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.FileModel;
import retrofit2.Call;
import retrofit2.Response;

public class GetFilesInfo extends AsyncTask<Void, Void, Collection<FileModel>> {
    private String authorization;
    private long doc_id;

    public GetFilesInfo(String authorization, long doc_id) {
        this.authorization = authorization;
        this.doc_id = doc_id;
    }

    @Override
    protected Collection<FileModel> doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        String authHeader = "Basic " + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP);
        Call<Collection<FileModel>> call = apiService.getFilesInfo(authHeader,doc_id);
        try {
            Response<Collection<FileModel>> response = call.execute();
            if (response.isSuccessful())
                return response.body();

        } catch (IOException e) {
            ServerConnection.setIOException(true);
            return null;
        }
        return null;
    }


}
