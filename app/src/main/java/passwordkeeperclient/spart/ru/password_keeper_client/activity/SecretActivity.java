package passwordkeeperclient.spart.ru.password_keeper_client.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import passwordkeeperclient.spart.ru.password_keeper_client.R;
import passwordkeeperclient.spart.ru.password_keeper_client.activity.additional.ExportDataCSV;
import passwordkeeperclient.spart.ru.password_keeper_client.activity.additional.SearchDataWatcher;
import passwordkeeperclient.spart.ru.password_keeper_client.activity.adapter.SecretListAdapter;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;
import passwordkeeperclient.spart.ru.password_keeper_client.credentianals.Principal;
import passwordkeeperclient.spart.ru.password_keeper_client.cryptography.CryptText;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.secret.DeleteSecret;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.secret.GetSecrets;

public class SecretActivity extends AppCompatActivity {
    private ListView secretListView;
    private ArrayList<SecretModel> secretListModels;
    private SecretListAdapter secretListAdapter;
    private boolean newSecret;
    private int selectedListPosition;
    private EditText searchData;

    private DrawerLayout drawer;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        secretListModels = new ArrayList<>();

        setContentView(R.layout.activity_secret);

        setNavigationMenuProperties();

        searchData = findViewById(R.id.searchData);

        selectedListPosition = 0;

        fillSecretList();

        secretListViewCreate();

        SearchDataWatcher searchDataWatcher =new SearchDataWatcher(secretListAdapter);
        searchData.addTextChangedListener(searchDataWatcher);

    }

    private void setNavigationMenuProperties(){
        Toolbar toolbar = findViewById(R.id.secretToolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(
                this,drawer, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawer.addDrawerListener(toogle);
        toogle.syncState();

        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUserName = (TextView)headerView.findViewById(R.id.userName);
        navUserName.setText(Principal.getLogin());

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 switch (item.getItemId()){
                     case R.id.secrets: {
                          break;
                     }
                     case R.id.docs: {
                         showDocActivity();
                         break;
                     }
                     case R.id.notes:{
                         showNoteActivity();
                         break;
                     }
                     case R.id.sign_out:{
                         resetLoginPassword();

                     }
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
            }
        });
    }

    private void resetLoginPassword(){
        Principal.setSessionId(null);
        Principal.setLogin(null);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Login", "");
            editor.putString("Password", "");
            editor.putBoolean("RememberIsChecked",true);

        editor.commit();

        this.finish();
        Intent intent = new Intent(getBaseContext(), EnterActivity.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshSecretListAdapter();
        navigationView.setCheckedItem(R.id.secrets);
    }

    public void fillSecretList() {
        ArrayList<SecretModel> secretModels = null;

        secretModels = new ArrayList<>(getSecrets());

        Long id;
        String description;
        String login;
        String password;

        if (!secretModels.isEmpty()) {
            for (SecretModel secret : secretModels) {

                id = secret.getId();
                description = CryptText.decryptString(secret.getDescription());
                login = CryptText.decryptString(secret.getLogin());
                password = CryptText.decryptString(secret.getPassword());

                SecretModel model = new SecretModel(id, description, login, password);
                secretListModels.add(model);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_secret, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_export_csv: {
                exportDialog();
                return true;
            }
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


    public Collection<SecretModel> getSecrets() {
        GetSecrets getSecrets = new GetSecrets();
        try {
            return getSecrets.execute().get();
        } catch (Exception e) {
            Toast.makeText(this, "getFilesInfo:" + e.toString(), Toast.LENGTH_LONG).show();
        }
        return null;
    }

    public void editSecret(SecretModel secretModel) {
        Intent intent = new Intent(getBaseContext(), EditSecretActivity.class);
        intent.putExtra("SecretModel", secretModel);
        startActivityForResult(intent,1);
    }

    private void showDocActivity(){
        Intent intent = new Intent(getBaseContext(), DocActivity.class);
        startActivity(intent);
    }

    private void showNoteActivity(){
        Intent intent = new Intent(getBaseContext(), NoteActivity.class);
        startActivity(intent);
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

        builder.setTitle("Warning!");
        builder.setMessage("Delete "+secretDescription+"?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                DeleteSecret deleteSecret = new DeleteSecret(secretId);
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

    private void exportDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Warning!");
        builder.setMessage("Do you want export all secrets?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                ExportDataCSV exportDataCSV = new ExportDataCSV();
                String result = exportDataCSV.exportData(secretListModels);
                Toast.makeText(SecretActivity.this, result, Toast.LENGTH_SHORT).show();

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

