package passwordkeeperclient.spart.ru.password_keeper_client.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import passwordkeeperclient.spart.ru.password_keeper_client.R;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.AddUser;

public class RegistrationActivity extends AppCompatActivity {
    private EditText loginTxt;
    private EditText passwordTxt;
    private EditText confirmPasswordTxt;
    private EditText emailTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        loginTxt = findViewById(R.id.loginText);
        passwordTxt = findViewById(R.id.passwordText);
        confirmPasswordTxt = findViewById(R.id.confirmPasswordText);
        emailTxt = findViewById(R.id.emailText);
        Button registrationBtn = findViewById(R.id.registrationBtn);
        ApiService apiService = ApiConnection.getApiService();

        registrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = loginTxt.getText().toString();
                String password = passwordTxt.getText().toString();
                String confirmPassword = confirmPasswordTxt.getText().toString();
                String email = emailTxt.getText().toString();

                if (!login.equals("")&&
                        !password.equals("")&&
                        !confirmPassword.equals("")&&
                        !email.equals("")&&
                         email.contains("@")) {
                    if (checkPasswords(password, confirmPassword)) {
                        AddUser addUser = new AddUser(login, password, email);
                        try {
                            if (addUser.execute().get()) {
                                Toast.makeText(getApplicationContext(), "Successfull registration ", Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else
                                Toast.makeText(getApplicationContext(), "User or e-mail is already exist", Toast.LENGTH_LONG).show();
                        } catch (InterruptedException e) {
                            Toast.makeText(getApplicationContext(), "Error:" + e.toString(), Toast.LENGTH_LONG).show();
                        } catch (ExecutionException e) {
                            Toast.makeText(getApplicationContext(), "Error:" + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    } else
                        Toast.makeText(getApplicationContext(), "Confirm the password correctly", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Check the information", Toast.LENGTH_LONG).show();
            }
        });

    }


    boolean checkPasswords (String password, String confirmPassword){
        return password.equals(confirmPassword);
    }

}
