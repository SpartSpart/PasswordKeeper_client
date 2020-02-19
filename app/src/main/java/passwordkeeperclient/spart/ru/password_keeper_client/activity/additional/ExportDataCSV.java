package passwordkeeperclient.spart.ru.password_keeper_client.activity.additional;

import android.os.Environment;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import passwordkeeperclient.spart.ru.password_keeper_client.activity.SecretActivity;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;

/**
 * Created by Pamela on 07.12.2019.
 */

public class ExportDataCSV {

    private static final char DEFAULT_SEPARATOR = ';';

//    public void exportDataToFile(ArrayList<SecretModel> secretModels) throws IOException {
//        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
//        String fileName = "PasswordKeeperData.csv";
//        String filePath = baseDir + File.separator + fileName;
//        File exportFile = new File(filePath);
//        CSVWriter writer;
//
////        if(exportFile.exists()&&!exportFile.isDirectory())
////        {
////            FileWriter fileWriter = new FileWriter(filePath, true);
////            writer = new CSVWriter(fileWriter);
////        }
////        else
//        {
//            writer = new CSVWriter(new FileWriter(filePath));
//
//        }
//
//        for (SecretModel secretModel:secretModels){
//            String[] secret = {secretModel.getDescription(), secretModel.getLogin(),secretModel.getPassword()};
//            writer.writeNext(secret);
//        }
//
//        writer.close();
//    }




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

        String[] login = SecretActivity.authorization.split(":");
        String fileName = "PasswordKeeperData("+login[0]+").csv";

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
