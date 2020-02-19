package passwordkeeperclient.spart.ru.password_keeper_client.requests.secret;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Pamela on 16.11.2019.
 */

public class UpdateSecret  extends AsyncTask<Void, Void, Boolean> {

    private String authorization;
    private Long id;
    private SecretModel secretModel;


    public UpdateSecret(String authorization, Long id, SecretModel secretModel) {
        this.authorization = authorization;
        this.id = id;
        this.secretModel = secretModel;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        String authHeader = "Basic " + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP);
        Call<Void> call = apiService.updateSecret(authHeader,id, secretModel);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful())
                return true ;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
