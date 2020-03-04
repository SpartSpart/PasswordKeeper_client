package passwordkeeperclient.spart.ru.password_keeper_client.requests.files;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;
import java.util.List;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.credentianals.Principal;
import retrofit2.Call;
import retrofit2.Response;


public class DeleteFiles extends AsyncTask<Void, Void, Boolean> {

    private String sessionId;
    private List<Long> idFileList;

    public DeleteFiles(List<Long> idFileList) {
        this.sessionId = Principal.getSessionId();
        this.idFileList = idFileList;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

       Call<Void> call = apiService.deleteFiles(sessionId, idFileList);
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
