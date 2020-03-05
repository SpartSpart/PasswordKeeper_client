package passwordkeeperclient.spart.ru.password_keeper_client.requests.notes;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.Collection;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ServerConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.NoteModel;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;
import passwordkeeperclient.spart.ru.password_keeper_client.credentianals.Principal;
import retrofit2.Call;
import retrofit2.Response;

public class GetNotes extends AsyncTask<Void, Void, Collection<NoteModel>> {
    private String sessionId;

    public GetNotes() {
        this.sessionId = Principal.getSessionId();
    }

    @Override
    protected Collection<NoteModel> doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        Call<Collection<NoteModel>> call = apiService.getNotes(sessionId);
        try {
            Response<Collection<NoteModel>> response = call.execute();
            if (response.isSuccessful())
                return response.body();

        } catch (IOException e) {
            ServerConnection.setIOException(true);
            return null;
        }
        return null;
    }


}
