package passwordkeeperclient.spart.ru.password_keeper_client.resonses;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;
import java.util.List;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Pamela on 15.11.2018.
 */

public class UpdateSecrets extends AsyncTask<Void, Void, Boolean> {
    private String authorization;
    private List<SecretModel> secretModels;

    public UpdateSecrets(String authorization, List<SecretModel> secretModels) {
        this.authorization = authorization;
        this.secretModels = secretModels;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        String authHeader = "Basic " + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP);
        Call<Void> call = apiService.updateSecrets(authHeader, secretModels);
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
