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

    @SerializedName("file_name")
    @Expose
    private String fileName;

    @SerializedName("doc_id")
    @Expose
    private long docId;

    @SerializedName("file_path")
    @Expose
    private String filePath;

    public FileModel(long id, String fileName, long docId, String filePath) {
        this.id = id;
        this.fileName = fileName;
        this.docId = docId;
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

    public long getDocId() {
        return docId;
    }

    public void setDocId(long docId) {
        this.docId = docId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
