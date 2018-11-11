package passwordkeeperclient.spart.ru.password_keeper_client.resonses;

import android.os.AsyncTask;

import java.io.IOException;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.UserModel;

/**
 * Created by Pamela on 11.11.2018.
 */

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

        try {
            apiService.addUser(new UserModel(login, password, email)).execute();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}