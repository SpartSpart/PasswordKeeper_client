package passwordkeeperclient.spart.ru.password_keeper_client.requests.secret;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.Collection;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ServerConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;
import passwordkeeperclient.spart.ru.password_keeper_client.credentianals.Principal;
import retrofit2.Call;
import retrofit2.Response;

public class GetSecrets extends AsyncTask<Void, Void, Collection<SecretModel>> {
    private String sessionId;

    public GetSecrets() {
        this.sessionId = Principal.getSessionId();
    }

    @Override
    protected Collection<SecretModel> doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        //String authHeader = "Basic " + Base64.encodeToString(sessionId.getBytes(), Base64.NO_WRAP);
        Call<Collection<SecretModel>> call = apiService.getSecrests(sessionId);
        try {
            Response<Collection<SecretModel>> response = call.execute();
            if (response.isSuccessful())
                return response.body();

        } catch (IOException e) {
            ServerConnection.setIOException(true);
            return null;
        }
        return null;
    }


}
