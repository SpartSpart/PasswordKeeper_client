package passwordkeeperclient.spart.ru.password_keeper_client.resonses;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;
import retrofit2.Call;
import retrofit2.Response;


public class AddSecret extends AsyncTask<Void, Void, Long> {
    private String authorization;
    private String description;
    private String login;
    private String password;

    public AddSecret(String authorization, String description, String login, String password) {
        this.authorization = authorization;
        this.description = description;
        this.login = login;
        this.password = password;
    }


    @Override
    protected Long doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        String authHeader = "Basic " + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP);
        Call<Long> call = apiService.addSecret(authHeader, new SecretModel(description,login,password));
        try {
            Response<Long> response = call.execute();
            if (response.isSuccessful())
                return response.body();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
