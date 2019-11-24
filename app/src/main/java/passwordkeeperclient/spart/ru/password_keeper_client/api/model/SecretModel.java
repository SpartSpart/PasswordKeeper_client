package passwordkeeperclient.spart.ru.password_keeper_client.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SecretModel implements Serializable {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("password")
    @Expose
    private String password;

    public SecretModel(long id, String description, String login, String password) {
        this.id = id;
        this.description = description;
        this.login = login;
        this.password = password;
    }

    public SecretModel(String description, String login, String password) {
        this.description = description;
        this.login = login;
        this.password = password;
    }

    public void setDescription(String description) {this.description = description;}

    public String getDescription() {
        return description;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}
}