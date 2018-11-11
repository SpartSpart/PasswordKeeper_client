package passwordkeeperclient.spart.ru.password_keeper_client.resonses;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;
import java.util.Collection;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.UserApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;
import retrofit2.Call;
import retrofit2.Response;

public class GetSecrets extends AsyncTask<Void, Void, Collection<SecretModel>> {
    private String authorization;

    public GetSecrets(String authorization) {
        this.authorization = authorization;
    }

    @Override
    protected Collection<SecretModel> doInBackground(Void... voids) {

        ApiService apiService = UserApiConnection.getApiService();

        String authHeader = "Basic " + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP);
        Call<Collection<SecretModel>> call = apiService.getSecrests(authHeader);

        try {
            Response<Collection<SecretModel>> response = call.execute();
            if (response.isSuccessful())
                return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


}
