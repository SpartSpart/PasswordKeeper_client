package passwordkeeperclient.spart.ru.password_keeper_client.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import passwordkeeperclient.spart.ru.password_keeper_client.R;
import passwordkeeperclient.spart.ru.password_keeper_client.activity.additional.SearchDataWatcher;
import passwordkeeperclient.spart.ru.password_keeper_client.activity.additional.SecretListAdapter;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;
import passwordkeeperclient.spart.ru.password_keeper_client.cryptography.Crypto;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.DeleteSecret;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.GetSecrets;

public class MainActivity extends AppCompatActivity {
    private ListView secretListView;
    private ArrayList<SecretModel> secretListModels;
    private SecretListAdapter secretListAdapter;
    public static String authorization;
    private boolean newSecret;
    private int selectedListPosition;
    private EditText searchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        secretListModels = new ArrayList<>();

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        searchData = findViewById(R.id.searchData);

        authorization = getIntent().getStringExtra("Authorization");

        selectedListPosition = 0;

        fillSecretList();

        secretListViewCreate();

        SearchDataWatcher searchDataWatcher =new SearchDataWatcher(secretListAdapter);
        searchData.addTextChangedListener(searchDataWatcher);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshSecretListAdapter();
    }


    public void fillSecretList() {
        ArrayList<SecretModel> secretModels = new ArrayList<>(getSecrets(authorization));
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

                SecretModel model = new SecretModel(id, description, login, password);
                secretListModels.add(model);
            }

        }

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
            case R.id.action_refresh: {
                secretListModels.clear();
                fillSecretList();
                secretListAdapter.getFilter().filter(null);
                secretListAdapter.notifyDataSetChanged();
                searchData.setText("");
                return true;
            }
            case R.id.action_new: {
                editSecret(null);
                newSecret = true;
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

    public void editSecret(SecretModel secretModel) {
        Intent intent = new Intent(getBaseContext(), EditSecretActivity.class);
        intent.putExtra("SecretModel", secretModel);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        SecretModel secretModel = (SecretModel) data.getSerializableExtra("SecretModelReturn");
        try {
            if (newSecret)  //проверка на новую запись или обновляемую
                secretListModels.add(secretModel);
            else {

                  secretListModels.get(selectedListPosition).setDescription(secretModel.getDescription());
                  secretListModels.get(selectedListPosition).setLogin(secretModel.getLogin());
                  secretListModels.get(selectedListPosition).setPassword(secretModel.getPassword());
            }

        }
        catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        selectedListPosition = 0;
    }

    private void secretListViewCreate() {

        secretListAdapter = new SecretListAdapter(this, secretListModels);

        secretListView = findViewById(R.id.secretListView);
        secretListView.setTextFilterEnabled(true);
        secretListView.setFocusable(true);

        secretListView.setAdapter(secretListAdapter);
        secretListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long l) {
                editSecret((SecretModel) parent.getItemAtPosition(pos));
                selectedListPosition = pos;
                newSecret = false;
            }
        });
        secretListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> secrets, View arg1,
                                           int pos, long id) {
                SecretModel secretModel = (SecretModel)secrets.getItemAtPosition(pos);
                deleteDialog(secretModel.getId(),secretModel.getDescription(), pos);
                return true;
            }
        });

    }

    private void deleteDialog(final Long secretId, final String secretDescription, final int listViewPosition){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Delete Secret");
        builder.setMessage("Delete "+secretDescription+"?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                DeleteSecret deleteSecret = new DeleteSecret(authorization, secretId);
                deleteSecret.execute();

                secretListModels.remove(listViewPosition);
                refreshSecretListAdapter();

                dialog.dismiss();
            }
        });


        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void refreshSecretListAdapter(){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                secretListModels.clear();
                fillSecretList();
                secretListAdapter.notifyDataSetChanged();
                refreshFilteredSecrets();
            }
        });
    }

    private void refreshFilteredSecrets(){
                secretListAdapter.getFilter().filter(searchData.getText());
        }

}
