package passwordkeeperclient.spart.ru.password_keeper_client.requests.documents;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.DocModel;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;
import retrofit2.Call;
import retrofit2.Response;


public class UpdateDoc extends AsyncTask<Void, Void, Boolean> {

    private String authorization;
    private Long id;
    private DocModel docModel;


    public UpdateDoc(String authorization, Long id, DocModel docModel) {
        this.authorization = authorization;
        this.id = id;
        this.docModel = docModel;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        String authHeader = "Basic " + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP);
        Call<Void> call = apiService.updateDoc(authHeader,id, docModel);
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
