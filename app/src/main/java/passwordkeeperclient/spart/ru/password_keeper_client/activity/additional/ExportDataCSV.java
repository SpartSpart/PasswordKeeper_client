package passwordkeeperclient.spart.ru.password_keeper_client.activity.additional;

import android.os.Environment;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.security.Principal;
import java.util.ArrayList;

import passwordkeeperclient.spart.ru.password_keeper_client.activity.SecretActivity;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;

/**
 * Created by Pamela on 07.12.2019.
 */

public class ExportDataCSV {

    private static final char DEFAULT_SEPARATOR = ';';

    private void writeCSV(Writer csvWriter, ArrayList<SecretModel> secretModels) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (SecretModel secret : secretModels) {
            sb.append(secret.getDescription()).append(DEFAULT_SEPARATOR).
                    append(secret.getLogin()).append(DEFAULT_SEPARATOR).
                    append(secret.getPassword());
            sb.append("\n");
        }

        csvWriter.append(sb.toString());
        csvWriter.flush();
        csvWriter.close();
    }

    public  String exportData(ArrayList<SecretModel> secretModels)  {
        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        File appDir = new File(baseDir + "/Password-Keeper");
        if (!appDir.exists()) {
            appDir.mkdir();
        }

        String login = passwordkeeperclient.spart.ru.password_keeper_client.credentianals.Principal.getLogin();
        String fileName = "Secrets("+login+").csv";

        File file = new File (appDir, fileName);
        if (file.exists ())
            file.delete ();
        String filePath = file.getAbsolutePath();
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            writeCSV(fileWriter,secretModels);
        } catch (IOException e) {
            return "Error :" + e.toString();
        }
        return "File saved: " + filePath;
    }
}
