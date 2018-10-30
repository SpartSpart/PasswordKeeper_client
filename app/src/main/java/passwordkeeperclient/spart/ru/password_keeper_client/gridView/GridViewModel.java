package passwordkeeperclient.spart.ru.password_keeper_client.gridView;

public class GridViewModel {
    private String description;
    private String login;
    private String password;

    public GridViewModel(String description, String login, String password) {
        this.description = description;
        this.login = login;
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

}
