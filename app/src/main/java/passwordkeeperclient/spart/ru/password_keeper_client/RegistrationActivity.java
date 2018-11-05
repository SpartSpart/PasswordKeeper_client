package passwordkeeperclient.spart.ru.password_keeper_client;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import passwordkeeperclient.spart.ru.password_keeper_client.server.ApiService;
import passwordkeeperclient.spart.ru.password_keeper_client.server.ApiUtils;
import passwordkeeperclient.spart.ru.password_keeper_client.server.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    private EditText login;
    private EditText password;
    private EditText confirmPassword;
    private EditText email;
    private Button registrationBtn;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        login = findViewById(R.id.loginText);
        password = findViewById(R.id.passwordText);
        confirmPassword = findViewById(R.id.confirmPasswordText);
        email = findViewById(R.id.emailText);
        registrationBtn = findViewById(R.id.registrationBtn);
        apiService = ApiUtils.getApiService();

    }


    public void saveRegistration(View view) {
        //retrofit magic
        try {
            addUser("ads", "asd", "asd"); //Values from textEdit forms
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addUser(final String login, String password, String email) throws Exception {
        apiService.addUser(login, password, email).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {

                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }
//
//    public void showResponse(String response) {
//        if(mResponseTv.getVisibility() == View.GONE) {
//            mResponseTv.setVisibility(View.VISIBLE);
//        }
//        mResponseTv.setText(response);
//    }


}
