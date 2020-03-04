package passwordkeeperclient.spart.ru.password_keeper_client.requests.secret;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;
import java.util.List;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;
import passwordkeeperclient.spart.ru.password_keeper_client.credentianals.Principal;
import retrofit2.Call;
import retrofit2.Response;


public class UpdateSecrets extends AsyncTask<Void, Void, Boolean> {
    private String sessionId;
    private List<SecretModel> secretModels;

    public UpdateSecrets(List<SecretModel> secretModels) {
        this.sessionId = Principal.getSessionId();
        this.secretModels = secretModels;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        //String authHeader = "Basic " + Base64.encodeToString(sessionId.getBytes(), Base64.NO_WRAP);
        Call<Void> call = apiService.updateSecrets(sessionId, secretModels);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful())
                return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
