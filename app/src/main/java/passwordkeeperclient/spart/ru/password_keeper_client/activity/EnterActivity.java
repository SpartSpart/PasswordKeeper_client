package passwordkeeperclient.spart.ru.password_keeper_client.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import passwordkeeperclient.spart.ru.password_keeper_client.R;
import passwordkeeperclient.spart.ru.password_keeper_client.credentianals.Principal;
import passwordkeeperclient.spart.ru.password_keeper_client.cryptography.CryptFile;
import passwordkeeperclient.spart.ru.password_keeper_client.cryptography.CryptText;
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

                        CryptText.setKeys(loginEditTxt.getText().toString());
                        CryptFile.setKey(loginEditTxt.getText().toString());

                        rememberLoginPasswordToMemory(rememberCheckBox.isChecked());

                            Principal.setLogin(loginEditTxt.getText().toString());
                            Principal.setSessionId("JSESSIONID="+sessionId);

                        Intent intent = new Intent(getBaseContext(), SecretActivity.class);
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
