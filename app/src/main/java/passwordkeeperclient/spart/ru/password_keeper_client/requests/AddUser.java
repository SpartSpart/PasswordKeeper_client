package passwordkeeperclient.spart.ru.password_keeper_client.requests;

import android.os.AsyncTask;

import java.io.IOException;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.UserModel;
import retrofit2.Call;
import retrofit2.Response;


public class AddUser extends AsyncTask <Void,Void,Boolean> {
    private String login;
    private String password;
    private String email;

    public AddUser(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        ApiService apiService = ApiConnection.getApiService();
        Call<Void> call = apiService.addUser(new UserModel(login, password, email));
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful())
                 return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}