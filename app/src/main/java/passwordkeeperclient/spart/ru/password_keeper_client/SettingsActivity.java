package passwordkeeperclient.spart.ru.password_keeper_client;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {
    private EditText host;
    private EditText port;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        host = findViewById(R.id.host);
        port = findViewById(R.id.port);
        Toolbar toolbar = findViewById(R.id.settingToolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        host.setText(getHost());
        port.setText(getPort());


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            String preferenceHost = host.getText().toString();
            String preferencePort = port.getText().toString();
            editor.putString("Host",preferenceHost);
            editor.putString("Port",preferencePort);
            editor.commit();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    String getHost(){
        return sharedPreferences.getString("Host","");
    }

    String getPort(){
        return sharedPreferences.getString("Port","");
    }


}
