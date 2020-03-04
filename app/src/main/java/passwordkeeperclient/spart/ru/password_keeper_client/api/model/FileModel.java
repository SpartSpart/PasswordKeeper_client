package passwordkeeperclient.spart.ru.password_keeper_client.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pamela on 19.02.2020.
 */

public class FileModel {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("fileName")
    @Expose
    private String fileName;

    @SerializedName("doc_id")
    @Expose
    private long doc_id;

    @SerializedName("filePath")
    @Expose
    private String filePath;

    public FileModel(long id, String fileName, long doc_id, String filePath) {
        this.id = id;
        this.fileName = fileName;
        this.doc_id = doc_id;
        this.filePath = filePath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(long doc_id) {
        this.doc_id = doc_id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
