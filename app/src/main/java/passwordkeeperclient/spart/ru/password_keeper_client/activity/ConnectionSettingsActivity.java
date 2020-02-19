package passwordkeeperclient.spart.ru.password_keeper_client.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import passwordkeeperclient.spart.ru.password_keeper_client.R;

public class ConnectionSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_settings);
        Toolbar toolbar = findViewById(R.id.connectionSettingsToolbar);
        setSupportActionBar(toolbar);
    }
}
