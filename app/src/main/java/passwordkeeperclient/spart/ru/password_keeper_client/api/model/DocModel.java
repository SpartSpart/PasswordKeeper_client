package passwordkeeperclient.spart.ru.password_keeper_client.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Pamela on 19.02.2020.
 */

public class DocModel implements Serializable {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("document")
    @Expose
    private String document;

    @SerializedName("description")
    @Expose
    private String description;

    public DocModel(long id, String document, String description) {
        this.id = id;
        this.document = document;
        this.description = description;
    }

    public DocModel(String document, String description) {
        this.document = document;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}