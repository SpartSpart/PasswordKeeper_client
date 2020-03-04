package passwordkeeperclient.spart.ru.password_keeper_client.requests.files;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;
import java.util.Collection;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ServerConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.FileModel;
import passwordkeeperclient.spart.ru.password_keeper_client.credentianals.Principal;
import retrofit2.Call;
import retrofit2.Response;

public class GetFilesInfo extends AsyncTask<Void, Void, Collection<FileModel>> {
    private String sessionId;
    private long doc_id;

    public GetFilesInfo(long doc_id) {
        this.sessionId = Principal.getSessionId();
        this.doc_id = doc_id;
    }

    @Override
    protected Collection<FileModel> doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        Call<Collection<FileModel>> call = apiService.getFilesInfo(sessionId,doc_id);
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
