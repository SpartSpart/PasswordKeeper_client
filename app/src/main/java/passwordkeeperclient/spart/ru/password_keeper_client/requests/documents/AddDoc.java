package passwordkeeperclient.spart.ru.password_keeper_client.requests.documents;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.DocModel;
import retrofit2.Call;
import retrofit2.Response;


public class AddDoc extends AsyncTask<Void, Void, Long> {
    private String authorization;
    private DocModel docModel;

    public AddDoc(String authorization, DocModel docModel) {
        this.authorization = authorization;
        this.docModel = docModel;
    }


    @Override
    protected Long doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        String authHeader = "Basic " + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP);
        Call<Long> call = apiService.addDoc(authHeader, docModel);
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