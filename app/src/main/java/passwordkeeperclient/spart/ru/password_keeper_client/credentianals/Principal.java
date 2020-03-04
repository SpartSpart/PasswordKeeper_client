package passwordkeeperclient.spart.ru.password_keeper_client.credentianals;

/**
 * Created by Pamela on 03.03.2020.
 */

public class Principal {
    private static String sessionId;
    private static String login;

    public static String getSessionId() {
        return sessionId;
    }

    public static void setSessionId(String sessionId) {
        Principal.sessionId = sessionId;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        Principal.login = login;
    }
}
