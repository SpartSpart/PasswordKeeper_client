package passwordkeeperclient.spart.ru.password_keeper_client.requests.files;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ServerConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.credentianals.Principal;
import retrofit2.Call;
import retrofit2.Response;

public class UpdateFileName extends AsyncTask<Void, Void, Boolean> {
    private String sessionId;
    private String newFileName;
    private long file_id;

    public UpdateFileName(long file_id, String newFileName) {
        this.sessionId = Principal.getSessionId();
        this.newFileName = newFileName;
        this.file_id = file_id;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        Call<Boolean> call = apiService.updateFileName(sessionId,file_id, newFileName);

        try {
            Response<Boolean> response = call.execute();
            if (response.isSuccessful())
                return response.body();

        } catch (IOException e) {
            ServerConnection.setIOException(true);
            return null;
        }
        return null;
    }


}
