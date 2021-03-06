package passwordkeeperclient.spart.ru.password_keeper_client.requests.documents;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;
import java.util.Collection;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ServerConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.DocModel;
import passwordkeeperclient.spart.ru.password_keeper_client.credentianals.Principal;
import retrofit2.Call;
import retrofit2.Response;

public class GetDocs extends AsyncTask<Void, Void, Collection<DocModel>> {
    private String sessionId;

    public GetDocs() {
        this.sessionId = Principal.getSessionId();
    }

    @Override
    protected Collection<DocModel> doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        Call<Collection<DocModel>> call = apiService.getDocs(sessionId);
        try {
            Response<Collection<DocModel>> response = call.execute();
            if (response.isSuccessful())
                return response.body();

        } catch (IOException e) {
            ServerConnection.setIOException(true);
            return null;
        }
        return null;
    }


}
