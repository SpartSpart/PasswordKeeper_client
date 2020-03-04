package passwordkeeperclient.spart.ru.password_keeper_client.requests.documents;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.DocModel;
import passwordkeeperclient.spart.ru.password_keeper_client.credentianals.Principal;
import retrofit2.Call;
import retrofit2.Response;


public class AddDoc extends AsyncTask<Void, Void, Long> {
    private DocModel docModel;
    private String sessionId;

    public AddDoc(DocModel docModel) {
        this.sessionId = Principal.getSessionId();
        this.docModel = docModel;
    }


    @Override
    protected Long doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        Call<Long> call = apiService.addDoc(sessionId, docModel);
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
