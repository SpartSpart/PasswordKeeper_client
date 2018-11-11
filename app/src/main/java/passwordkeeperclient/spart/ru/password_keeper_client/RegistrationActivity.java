package passwordkeeperclient.spart.ru.password_keeper_client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import passwordkeeperclient.spart.ru.password_keeper_client.api.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.api.UserApiConnection;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.UserModel;
import passwordkeeperclient.spart.ru.password_keeper_client.resonses.AddUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    private EditText loginTxt;
    private EditText passwordTxt;
    private EditText confirmPasswordTxt;
    private EditText emailTxt;
    private Button registrationBtn;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        loginTxt = findViewById(R.id.loginText);
        passwordTxt = findViewById(R.id.passwordText);
        confirmPasswordTxt = findViewById(R.id.confirmPasswordText);
        emailTxt = findViewById(R.id.emailText);
        registrationBtn = findViewById(R.id.registrationBtn);
        apiService = UserApiConnection.getApiService();

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
                            }
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


//    public void addUser(final String login, String password, String email) throws Exception {
//
//            apiService.addUser(new UserModel(login,password,email)).enqueue(new Callback<Void>() {
//
//
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                loginTxt.setText("response = " +response.toString());
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                loginTxt.setText("call = " + call.toString());
//            }
//        });

//    }
////
//    public void showResponse(String response) {
////        if(loginTxt.getVisibility() == View.GONE) {
////            mResponseTv.setVisibility(View.VISIBLE);
////        }
//
//        loginTxt.setText(response);
//    }

    boolean checkPasswords (String password, String confirmPassword){
        if(password.equals(confirmPassword))
            return true;
        return false;
    }

}
