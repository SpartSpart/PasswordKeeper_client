package passwordkeeperclient.spart.ru.password_keeper_client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {
    private EditText host;
    private EditText port;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        host = findViewById(R.id.host);
        port = findViewById(R.id.port);
        saveBtn = findViewById(R.id.saveBtn);
    }

    public void saveSettings(View view) {

    }
}
