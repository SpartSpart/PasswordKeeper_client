package passwordkeeperclient.spart.ru.password_keeper_client;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.UserApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;
import passwordkeeperclient.spart.ru.password_keeper_client.resonses.GetSecrets;
import passwordkeeperclient.spart.ru.password_keeper_client.resonses.LogIn;
import retrofit2.Call;
import retrofit2.Response;

import static java.util.Objects.nonNull;

public class EnterActivity extends AppCompatActivity {
    private EditText login;
    private EditText password;
    private Button enterBtn;
    private Button registrationBtn;
    private ProgressBar bar;
    private ApiService apiService;
    private String authorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        login = findViewById(R.id.loginText);
        password = findViewById(R.id.passwordText);
        enterBtn = findViewById(R.id.enterBtn);
        registrationBtn = findViewById(R.id.registrationBtn);
        bar = findViewById(R.id.progressBarRound);
        Toolbar toolbar = findViewById(R.id.enterToolbar);
        setSupportActionBar(toolbar);
        apiService = UserApiConnection.getApiService();


    }


    public void showMainActivity(View view) throws Exception {
        authorization = login.getText() + ":" + password.getText();
        LogIn logIn = new LogIn(authorization);
        String sessionId;

        {
            try {
                sessionId = logIn.execute().get();
                if (sessionId != null) {
                    Toast.makeText(getApplicationContext(), "Login Correct", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getBaseContext(),MainActivity.class);
                    intent.putExtra("Authorization",authorization);
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "Login/Password Incorrect", Toast.LENGTH_LONG).show();
            } catch (InterruptedException e) {
                Toast.makeText(getApplicationContext(), "Error: "+e.toString(), Toast.LENGTH_LONG).show();
            } catch (ExecutionException e) {
                Toast.makeText(getApplicationContext(), "Error: "+e.toString(), Toast.LENGTH_LONG).show();
            }
        }


//        authorization = login.getText() + ":" + password.getText();  //get secrets
//        authorization = "Jerry:0000";
//        GetSecrets getSecrets = new GetSecrets(authorization);
//
//        Collection<SecretModel> secretModels = getSecrets.execute().get();
//        Intent intent = new Intent(getBaseContext(), MainActivity.class);
//        intent.putExtra("SECRET_MODELS", new ArrayList<>(secretModels));
//        intent.putExtra("AUTHORIZATION", authorization);
//
//        startActivity(intent);
    }

    public void showRegistrationActivity(View view) {
        Intent intObj = new Intent(this, RegistrationActivity.class);
        startActivity(intObj);
    }

    public void showSettingsActivity(View view) {

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
