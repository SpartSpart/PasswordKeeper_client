package passwordkeeperclient.spart.ru.password_keeper_client.requests.notes;

import android.os.AsyncTask;

import java.io.IOException;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.NoteModel;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;
import passwordkeeperclient.spart.ru.password_keeper_client.credentianals.Principal;
import retrofit2.Call;
import retrofit2.Response;


public class AddNote extends AsyncTask<Void, Void, Long> {
    private String sessionId;
    private NoteModel noteModel;

    public AddNote(NoteModel noteModel) {
        this.sessionId = Principal.getSessionId();
        this.noteModel = noteModel;
    }


    @Override
    protected Long doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        Call<Long> call = apiService.addNote(sessionId, noteModel);
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
