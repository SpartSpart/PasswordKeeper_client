package passwordkeeperclient.spart.ru.password_keeper_client;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;
import passwordkeeperclient.spart.ru.password_keeper_client.cryptography.Crypto;
import passwordkeeperclient.spart.ru.password_keeper_client.listview.ListViewAdapter;
import passwordkeeperclient.spart.ru.password_keeper_client.listview.model.ListViewModel;
import passwordkeeperclient.spart.ru.password_keeper_client.resonses.AddSecret;
import passwordkeeperclient.spart.ru.password_keeper_client.resonses.GetSecrets;
import passwordkeeperclient.spart.ru.password_keeper_client.resonses.UpdateSecrets;

public class MainActivity extends AppCompatActivity {
    private ListView mainView;
    private ArrayList<ListViewModel> listViewModels;
    ListViewAdapter adapter;
    private String authorization;
    public static Set<Integer> changedID = new HashSet<>(); //For saving id of objects that were changed in the listView for further changing in DB


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listViewModels = new ArrayList<>();
        changedID.clear();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        mainView = findViewById(R.id.secretList);

        authorization = getIntent().getStringExtra("Authorization");

        ArrayList<SecretModel> secretModels = new ArrayList<>(getSecrets(authorization));

        buildListModel(secretModels);

    }

    public void buildListModel(ArrayList<SecretModel> secretModels) {
        Long id;
        String description;
        String login;
        String password;

        if (!secretModels.isEmpty()) {
            for (SecretModel secret : secretModels) {
                id = secret.getId();
                description = Crypto.decryptString(secret.getDescription());
                login = Crypto.decryptString(secret.getLogin());
                password = Crypto.decryptString(secret.getPassword());
                ListViewModel model = new ListViewModel(id, description, login, password);
                listViewModels.add(model);
            }

        }

        adapter = new ListViewAdapter(getApplicationContext(), 0, listViewModels);
        mainView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_save: {
                try {
                    updateSecrets();
                } catch (Exception e) {
                    Toast.makeText(this, "Error:" + e.toString(), Toast.LENGTH_LONG).show();
                }
                return true;
            }
            case R.id.action_new: {
                setSecretDialog("New Secret", "Enter Data", "", "", "");

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }


    public Collection<SecretModel> getSecrets(String authorization) {
        GetSecrets getSecrets = new GetSecrets(authorization);

        try {
            return getSecrets.execute().get();

        } catch (InterruptedException e) {
            Toast.makeText(this, "Error:" + e.toString(), Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            Toast.makeText(this, "Error:" + e.toString(), Toast.LENGTH_LONG).show();
        }

        return null;

    }

    void setSecretDialog(String title,
                         String message,
                         final String description,
                         final String login,
                         final String password) {


        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle(title);
        dialog.setMessage(message);

// Set an EditText view to get user input
        final EditText descriptionText = new EditText(this);
        final EditText loginText = new EditText(this);
        final EditText passwordText = new EditText(this);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);


        descriptionText.setText(description);
        loginText.setText(login);
        passwordText.setText(password);

        descriptionText.setHint("Description");
        loginText.setHint("Login");
        passwordText.setHint("Password");

        layout.addView(descriptionText);
        layout.addView(loginText);
        layout.addView(passwordText);

        dialog.setView(layout);

        dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String description = descriptionText.getText().toString();
                String login = loginText.getText().toString();
                String password = passwordText.getText().toString();

                if (description.equals("") && login.equals("") && password.equals(""))
                    return;

                AddSecret addSecret = null;
                try {
                    addSecret = new AddSecret(authorization,
                            Crypto.encryptString(description),
                            Crypto.encryptString(login),
                            Crypto.encryptString(password));
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Error: "+e.toString(), Toast.LENGTH_LONG).show();
                }


                try {
                    Long id = addSecret.execute().get();
                    if (id != null) {
                        listViewModels.add(new ListViewModel(id,
                                descriptionText.getText().toString(),
                                loginText.getText().toString(),
                                passwordText.getText().toString()));
                    } else
                        Toast.makeText(getBaseContext(), "Error: Something was wrong", Toast.LENGTH_LONG).show();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        dialog.show();
    }

    private List<SecretModel> secretsToUpdate() throws Exception {
        ArrayList<SecretModel> secretModels = new ArrayList<>();
//        HashSet <String> set= new HashSet <String>();
        Iterator iterator = changedID.iterator();
        while (iterator.hasNext()) {
            int i = (int) iterator.next();
            Long id = listViewModels.get(i).getId();
            String description = listViewModels.get(i).getDescription();
            String login = listViewModels.get(i).getLogin();
            String password = listViewModels.get(i).getPassword();
            if (description.equals("") && login.equals("") && password.equals(""))
                secretModels.add(new SecretModel(id, description, login, password));
            else
                secretModels.add(new SecretModel(id,
                        Crypto.encryptString(description),
                        Crypto.encryptString(login),
                        Crypto.encryptString(password)));
        }
        changedID.clear();
        return secretModels;
    }

    private void updateSecrets() throws Exception {
        List<SecretModel> secretModels = secretsToUpdate();
        boolean bool = false;
        if (!secretModels.isEmpty()) {
            UpdateSecrets updateSecrets = new UpdateSecrets(authorization, secretModels);
            try {
                bool = updateSecrets.execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        if (bool) {
            adapter.deleteVoidRows();
            Toast.makeText(getBaseContext(), "Updated", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(getBaseContext(), "No secrets to Update", Toast.LENGTH_LONG).show();
    }


}
