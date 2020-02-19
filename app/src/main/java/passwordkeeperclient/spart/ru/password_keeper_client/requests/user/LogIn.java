package passwordkeeperclient.spart.ru.password_keeper_client.requests.user;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiConnection;
import retrofit2.Call;
import retrofit2.Response;

public class LogIn extends AsyncTask<Void, Void, String>  {
private String authorization;
private Context context;
private final String SERVER_CONNECTION_FAILED = "Server connection failed";

    public LogIn(Context context, String authorization) {
        this.authorization = authorization;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String returnValue = new String();
        ApiService apiService = ApiConnection.getApiService();


        String authHeader = "Basic " + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP);
        Call<Void> call = apiService.loginUser(authHeader);

        Response<Void> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            return null;
        }
        if (response.isSuccessful()) {
                String fullCookie = response.headers().get("Set-Cookie");
                returnValue = (fullCookie.substring(0, fullCookie.indexOf(";"))).replace("JSESSIONID=", "");
        }
        return returnValue;
    }

}