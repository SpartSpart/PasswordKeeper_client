package passwordkeeperclient.spart.ru.password_keeper_client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {
    private EditText login;
    private EditText password;
    private EditText confirmPassword;
    private EditText email;
    private Button registrationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        login = findViewById(R.id.loginText);
        password = findViewById(R.id.passwordText);
        confirmPassword = findViewById(R.id.confirmPasswordText);
        email = findViewById(R.id.emailText);
        registrationBtn = findViewById(R.id.registrationBtn);
    }


    public void saveRegistration(View view) {
    }
}
