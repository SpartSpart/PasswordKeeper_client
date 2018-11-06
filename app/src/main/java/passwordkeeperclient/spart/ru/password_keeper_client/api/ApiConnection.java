package passwordkeeperclient.spart.ru.password_keeper_client.api;

public class ApiConnection {

        private ApiConnection() {}

        public static final String BASE_URL = "http://127.0.0.1:8080/api/user/";

        public static ApiService getApiService() {

            return RestClient.getClient(BASE_URL).create(ApiService.class);
        }
    }

