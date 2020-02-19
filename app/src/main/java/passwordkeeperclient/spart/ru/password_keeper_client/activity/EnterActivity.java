package passwordkeeperclient.spart.ru.password_keeper_client.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import passwordkeeperclient.spart.ru.password_keeper_client.R;
import passwordkeeperclient.spart.ru.password_keeper_client.cryptography.Crypto;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.user.LogIn;

public class EnterActivity extends AppCompatActivity {
    private EditText loginEditTxt;
    private EditText passwordEditTxt;
    private SharedPreferences sharedPreferences;
    private CheckBox rememberCheckBox;
    private final String SERVER_CONNECTION_FAILED = "Server connection failed";
    private final String WRONG_LOGIN_OR_PASSWORD = "Login/Password Incorrect";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.activity_enter);
        loginEditTxt = findViewById(R.id.loginText);
        passwordEditTxt = findViewById(R.id.passwordText);
        rememberCheckBox = findViewById(R.id.checkRememberLoginPassword);
        fillLoginPasswordFromMemory();
        Toolbar toolbar = findViewById(R.id.enterToolbar);
        setSupportActionBar(toolbar);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_enter, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        switch (id) {
//            case R.id.connection_settings: {
//                Intent intent = new Intent(getBaseContext(), ConnectionSettingsActivity.class);
//                //intent.putExtra("Authorization", authorization;
//                startActivity(intent);
//                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//
//        }
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    protected void onResume() {
        super.onResume();
        fillLoginPasswordFromMemory();
    }

    public void showMainActivity(View view) {
        String authorization = loginEditTxt.getText() + ":" + passwordEditTxt.getText();
        LogIn logIn = new LogIn(this, authorization);
        String sessionId;

        {
            try {
                sessionId = logIn.execute().get();

                if (sessionId == null)
                     Toast.makeText(this, SERVER_CONNECTION_FAILED, Toast.LENGTH_SHORT).show();
                     else
                        if (sessionId.isEmpty()){
                            Toast.makeText(this,WRONG_LOGIN_OR_PASSWORD, Toast.LENGTH_SHORT).show();
                    }
                     else {
                        Toast.makeText(getApplicationContext(), "Login Correct", Toast.LENGTH_LONG).show();

                        Crypto.setKeys(loginEditTxt.getText().toString());

                        rememberLoginPasswordToMemory(rememberCheckBox.isChecked());

                        Intent intent = new Intent(getBaseContext(), SecretActivity.class);
                        intent.putExtra("Authorization", authorization);
                        startActivity(intent);
                    }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }



    public void showRegistrationActivity(View view) {
        Intent intObj = new Intent(this, RegistrationActivity.class);
        startActivity(intObj);
    }


    public void forgetPassword(View view){
        Toast.makeText(this, "Doesn't work yet.", Toast.LENGTH_SHORT).show();
    }


    private void fillLoginPasswordFromMemory(){
        String login = sharedPreferences.getString("Login","");
        String password = sharedPreferences.getString("Password","");
        boolean checked = sharedPreferences.getBoolean("RememberIsChecked",false);
        loginEditTxt.setText(login);
        passwordEditTxt.setText(password);
        rememberCheckBox.setChecked(checked);
    }

    private void rememberLoginPasswordToMemory(boolean checked){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (checked){
            editor.putString("Login", loginEditTxt.getText().toString());
            editor.putString("Password", passwordEditTxt.getText().toString());
            editor.putBoolean("RememberIsChecked",checked);
        }
        else {
            editor.putString("Login", "");
            editor.putString("Password", "");
            editor.putBoolean("RememberIsChecked",false);
        }
        editor.commit();
    }

}
