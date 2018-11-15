package passwordkeeperclient.spart.ru.password_keeper_client.api;

import android.content.SharedPreferences;

import passwordkeeperclient.spart.ru.password_keeper_client.SettingsActivity;

public class ApiConnection {

    public static String BASE_URL="";
   // private SettingsActivity settingsActivity=new SettingsActivity();
    private ApiConnection() {}
       // String connection = settingsActivity.getHost()+":"+settingsActivity.getPort();
        //BASE_URL = "http://"+connection+"/api/";



    public static ApiService getApiService() throws IllegalArgumentException {

        return RestClient.getClient(BASE_URL).create(ApiService.class);
    }

    public static void setBaseUrl(String host, String port){
        BASE_URL = "http://"+host+":"+port+"/api/";
    }

}

