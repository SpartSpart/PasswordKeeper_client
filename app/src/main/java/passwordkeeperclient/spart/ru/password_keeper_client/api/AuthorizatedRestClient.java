package passwordkeeperclient.spart.ru.password_keeper_client.api;


import okhttp3.OkHttpClient;

public class AuthorizatedRestClient{
    private String username = "Jerry";
    private String password = "0000";
    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new Authorization(username, password))
            .build();

}
