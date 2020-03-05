package passwordkeeperclient.spart.ru.password_keeper_client.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NoteModel implements Serializable {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("note")
    @Expose
    private String note;

    public NoteModel(String note) {
        this.note = note;
    }

    public NoteModel(long id, String note) {
        this.id = id;
        this.note = note;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}