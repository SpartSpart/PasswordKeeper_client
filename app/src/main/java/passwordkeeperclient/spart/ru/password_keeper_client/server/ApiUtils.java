package passwordkeeperclient.spart.ru.password_keeper_client.server;

public class ApiUtils {

        private ApiUtils() {}

        public static final String BASE_URL = "http://localhost:8080/api/user/";

        public static ApiService getApiService() {

            return RestClient.getClient(BASE_URL).create(ApiService.class);
        }
    }

