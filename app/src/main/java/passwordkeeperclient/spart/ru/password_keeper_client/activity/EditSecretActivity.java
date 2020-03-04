package passwordkeeperclient.spart.ru.password_keeper_client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;
import passwordkeeperclient.spart.ru.password_keeper_client.R;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;
import passwordkeeperclient.spart.ru.password_keeper_client.cryptography.CryptText;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.secret.AddSecret;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.secret.UpdateSecret;

public class EditSecretActivity extends AppCompatActivity {

    private EditText secretDescription;
    private EditText secretLogin;
    private EditText secretPassword;

    private SecretModel secretModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_secret);

        Toolbar toolbar = findViewById(R.id.editToolbar);
        setSupportActionBar(toolbar);

        secretDescription = findViewById(R.id.secretDescription);
        secretLogin = findViewById(R.id.secretLogin);
        secretPassword = findViewById(R.id.secretPassword);

        secretModel = (SecretModel) getIntent().getSerializableExtra("SecretModel");
        if (secretModel != null) {

            secretDescription.setText(secretModel.getDescription());
            secretLogin.setText(secretModel.getLogin());
            secretPassword.setText(secretModel.getPassword());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_secret, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_cancel: {
                finish();
                return true;
            }
            case R.id.action_save: {
                try {

                    SecretModel secret = getSecret(
                            secretDescription.getText().toString(),
                            secretLogin.getText().toString(),
                            secretPassword.getText().toString()
                    );
                    saveSecret(secret);
                } catch (Exception e) {
                    Toast.makeText(this, "Save: " + e.toString(), Toast.LENGTH_SHORT).show();
                }
                    return true;
                }
            }
            return super.onOptionsItemSelected(item);
        }


    private void saveSecret(SecretModel secretModel) throws ExecutionException, InterruptedException {

        if (secretModel != null) {
            SecretModel newSecretModel = sendSecretToDB(secretModel);
            Intent intent = new Intent();
            intent.putExtra("SecretModelReturn", newSecretModel);
            setResult(1, intent);
            finish();
        }
    }

    private SecretModel getSecret(String description, String login, String password) throws ParseException {

        try {
            if (!description.equals("") &&
                    !login.equals("") &&
                    !password.equals("")){

                SecretModel secretModelLocal = secretModel;

                if (secretModelLocal != null) {
                    secretModelLocal = new SecretModel(secretModel.getId(), description, login, password);
                } else {
                    secretModelLocal = new SecretModel(description, login , password);
                }

                return secretModelLocal;
            } else Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "EditActivity: "+e.toString(), Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    private SecretModel sendSecretToDB(SecretModel secretModel) {

        Long id = secretModel.getId();

        try {
            SecretModel encryptedSecret = encryptSecret(secretModel);
            if (id == 0) {
                AddSecret addSecret = new AddSecret(encryptedSecret);
                id = addSecret.execute().get();
                secretModel.setId(id); //если новая запись, то нужно чтобы в листе отобразилась запись с id, иначе при редактировании -  создастся новая
            } else {
                UpdateSecret updateSecret = new UpdateSecret(secretModel.getId(), encryptedSecret);
                updateSecret.execute();
            }

        } catch (Exception e) {
            Toast.makeText(this, "sendSecretToDB: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
        return secretModel;
    }

    private SecretModel encryptSecret(SecretModel secretModel) throws Exception {
        SecretModel cryptedSecret = secretModel;

        cryptedSecret.setId(secretModel.getId());
        cryptedSecret.setDescription(CryptText.encryptString(secretModel.getDescription()));
        cryptedSecret.setLogin(CryptText.encryptString(secretModel.getLogin()));
        cryptedSecret.setPassword(CryptText.encryptString(secretModel.getPassword()));

        return cryptedSecret;
    }

}
