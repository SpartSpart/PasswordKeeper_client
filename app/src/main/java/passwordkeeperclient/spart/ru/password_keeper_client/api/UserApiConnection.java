package passwordkeeperclient.spart.ru.password_keeper_client.api;

public class UserApiConnection {

        private UserApiConnection() {}

        public static final String BASE_URL = "http://192.168.1.141:8080/api/";

        public static ApiService getApiService() {

            return RestClient.getClient(BASE_URL).create(ApiService.class);
        }
    }

