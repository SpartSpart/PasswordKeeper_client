package passwordkeeperclient.spart.ru.password_keeper_client.requests.documents;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import retrofit2.Call;
import retrofit2.Response;


public class DeleteDoc extends AsyncTask<Void, Void, Boolean> {

    private String authorization;
    private Long id;

    public DeleteDoc(String authorization, Long id) {
        this.authorization = authorization;
        this.id = id;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        String authHeader = "Basic " + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP);
        Call<Void> call = apiService.deleteDoc(authHeader,id);
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
