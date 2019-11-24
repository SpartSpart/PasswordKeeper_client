package passwordkeeperclient.spart.ru.password_keeper_client.requests;


import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import java.io.IOException;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiConnection;
import retrofit2.Call;
import retrofit2.Response;

public class LogIn extends AsyncTask<Void, Void, String> {
private String authorization;
private final String SERVER_CONNECTION_FAILED = "Server connection failed";

    public LogIn(String authorization) {
        this.authorization = authorization;
    }


    @Override
    protected String doInBackground(Void... voids) {
        ApiService apiService = ApiConnection.getApiService();

        String authHeader = "Basic " + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP);
        Call<Void> call = apiService.loginUser(authHeader);

        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                String fullCookie = response.headers().get("Set-Cookie");
                String sessionID = (fullCookie.substring(0, fullCookie.indexOf(";"))).replace("JSESSIONID=", "");
                return sessionID;
            }
        } catch (IOException e) {
            return SERVER_CONNECTION_FAILED;
        }

        return null;
    }
}