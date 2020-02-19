package passwordkeeperclient.spart.ru.password_keeper_client.api;

/**
 * Created by Pamela on 03.12.2019.
 */


public class ServerConnection {
    private static boolean isIOExceptionHappened =false;



    public static void setIOException(boolean IOExceptionHappened){
        isIOExceptionHappened = IOExceptionHappened;
    }

    public static boolean isServerConnectionOK(){
        if (!isIOExceptionHappened){
            return true;
        }
        else{
            isIOExceptionHappened = false;
            return false;
        }

    }


}