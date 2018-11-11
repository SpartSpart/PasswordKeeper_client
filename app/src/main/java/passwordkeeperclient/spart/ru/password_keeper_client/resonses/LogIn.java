package passwordkeeperclient.spart.ru.password_keeper_client.resonses;


import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.UserApiConnection;
import retrofit2.Call;
import retrofit2.Response;

public class LogIn extends AsyncTask<Void, Void, String> {
private String authorization;

    public LogIn(String authorization) {
        this.authorization = authorization;
    }


    @Override
    protected String doInBackground(Void... voids) {
        ApiService apiService = UserApiConnection.getApiService();

        String authHeader = "Basic " + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP);
        Call<Void> call = apiService.loginUser(authHeader);

        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
//                    String cookie = response.headers().get("Set-Cookie");
//                    if (nonNull(cookie)) {
//                        return Arrays.stream(cookie.split(";"))
//                                .map(String::trim)
//                                .map(e -> e.split("="))
//                                .filter(e -> e[0].equals("JSESSIONID"))
//                                .map(e -> e[1])
//                                .findAny();
//                    }
                String fullCookie = response.headers().get("Set-Cookie");
                String sessionID = (fullCookie.substring(0, fullCookie.indexOf(";"))).replace("JSESSIONID=", "");
                return sessionID;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}