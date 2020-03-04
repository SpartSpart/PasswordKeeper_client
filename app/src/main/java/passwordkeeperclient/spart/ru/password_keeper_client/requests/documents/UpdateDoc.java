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


public class UpdateDoc extends AsyncTask<Void, Void, Boolean> {

    private String sessionId;
    private Long id;
    private DocModel docModel;


    public UpdateDoc(Long id, DocModel docModel) {
        this.sessionId = Principal.getSessionId();
        this.id = id;
        this.docModel = docModel;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        Call<Void> call = apiService.updateDoc(sessionId,id, docModel);
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
