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

public class UpdateNote extends AsyncTask<Void, Void, Boolean> {

    private String sessionId;
    private Long id;
    private NoteModel noteModel;


    public UpdateNote(Long id, NoteModel noteModel) {
        this.sessionId = Principal.getSessionId();
        this.id = id;
        this.noteModel = noteModel;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        Call<Void> call = apiService.updateNote(sessionId,id, noteModel);
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
