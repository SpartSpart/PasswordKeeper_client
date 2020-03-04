package passwordkeeperclient.spart.ru.password_keeper_client.requests.files;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import okhttp3.ResponseBody;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.credentianals.Principal;
import passwordkeeperclient.spart.ru.password_keeper_client.cryptography.CryptFile;
import passwordkeeperclient.spart.ru.password_keeper_client.cryptography.CryptoException;
import retrofit2.Call;
import retrofit2.Response;


public class DownloadFiles extends AsyncTask<Void, Void, Boolean> {
    private final int MAX_FILE_SIZE = 30720;
    private String sessionId;
    private long file_id;
    private String fileName;
    private String docName;
    private String login;

    public DownloadFiles(long file_id, String docName, String fileName) {
        this.sessionId = Principal.getSessionId();
        this.file_id = file_id;
        this.docName = docName;
        this.fileName = fileName;
        this.login = Principal.getLogin();
    }


    @Override
    protected Boolean doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        Call<ResponseBody> call = apiService.getFile(sessionId, file_id);
        Response<ResponseBody> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.isSuccessful()) {

            boolean b = writeToDisk(response.body());
            Log.e("File saved: ", Boolean.toString(b));
            return true;
            }
            return false;
    }

    private boolean writeToDisk(ResponseBody body) {
        String finalPath = "/Password-Keeper/"+docName+"("+login+")"+File.separator;
        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        String appDir = baseDir + finalPath;
        try {
            File directory = new File(appDir);

            // Create the storage directory if it does not exist
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File(directory.getPath() + File.separator
                    + fileName);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[MAX_FILE_SIZE];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;
               }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }

                try {
                    CryptFile.decrypt(file,file);
                } catch (CryptoException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            Log.e("File exception: ", e.toString());
            return false;
        }
    }

}



