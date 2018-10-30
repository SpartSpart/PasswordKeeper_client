package passwordkeeperclient.spart.ru.password_keeper_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class EnterActivity extends AppCompatActivity {
    private EditText login;
    private EditText password;
    private Button enterBtn;
    private Button registrationBtn;
    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        login = findViewById(R.id.loginText);
        password = findViewById(R.id.passwordText);
        enterBtn =findViewById(R.id.enterBtn);
        registrationBtn =findViewById(R.id.registrationBtn);
        bar = findViewById(R.id.progressBarRound);
        Toolbar toolbar = findViewById(R.id.enterToolbar);
        setSupportActionBar(toolbar);

    }


    public void showMainActivity (View view){
//        EnterAsynkTask asynkTask = new EnterAsynkTask(bar);
//        asynkTask.execute();
        Intent intObj = new Intent(this, MainActivity.class);
        startActivity(intObj);
//        asynkTask.setProcessEnded(true);

    }

    public void showRegistrationActivity (View view){
        Intent intObj = new Intent(this, RegistrationActivity.class);
        startActivity(intObj);
    }

    public void showSettingsActivity (View view){
        Intent intObj = new Intent(this, SettingsActivity.class);
        startActivity(intObj);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_enter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showSettingsActivity(this.getCurrentFocus());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
