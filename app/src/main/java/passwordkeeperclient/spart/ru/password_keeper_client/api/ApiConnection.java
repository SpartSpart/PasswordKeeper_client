package passwordkeeperclient.spart.ru.password_keeper_client.api;

public class ApiConnection {

    private static String BASE_URL="";
    private ApiConnection() {}

    public static ApiService getApiService() throws IllegalArgumentException {

        return RestClient.getClient(BASE_URL).create(ApiService.class);
    }

    public static void setBaseUrl(String host, String port){
        BASE_URL = "http://"+host+":"+port+"/api/";
    }

}

