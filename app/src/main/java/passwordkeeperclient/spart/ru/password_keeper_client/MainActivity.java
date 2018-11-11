package passwordkeeperclient.spart.ru.password_keeper_client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;
import passwordkeeperclient.spart.ru.password_keeper_client.listview.ListViewAdapter;
import passwordkeeperclient.spart.ru.password_keeper_client.listview.model.ListViewModel;
import passwordkeeperclient.spart.ru.password_keeper_client.resonses.GetSecrets;

public class MainActivity extends AppCompatActivity {
    private ListView mainView;
    private ArrayList<ListViewModel> listViewModels;
    public static Set<Long> changedID = new HashSet<>(); //For saving id of objects that were changed in the listView for further changing in DB



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listViewModels= new ArrayList<>();
        changedID.clear();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        mainView = findViewById(R.id.secretList);

        String authorization = getIntent().getStringExtra("Authorization");

        ArrayList<SecretModel> secretModels = new ArrayList<>(getSecrets(authorization));

        buildListModel(secretModels);

    }

    public void buildListModel(ArrayList<SecretModel> secretModels) {

        if (!secretModels.isEmpty()){
            for(SecretModel secret: secretModels){
                ListViewModel model= new ListViewModel(
                        secret.getId(),
                        secret.getDescription(),
                        secret.getLogin(),
                        secret.getPassword()
                );
                listViewModels.add(model);
            }

        }

        ListViewAdapter adapter = new ListViewAdapter(getApplicationContext(), 0, listViewModels);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            Toast.makeText(this, changedID.toString(), Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Collection<SecretModel> getSecrets(String authorization){
        GetSecrets getSecrets = new GetSecrets(authorization);

        try {
            return getSecrets.execute().get();

        } catch (InterruptedException e) {
            Toast.makeText(this, "Error:"+e.toString(), Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            Toast.makeText(this, "Error:"+e.toString(), Toast.LENGTH_LONG).show();
        }

        return null;

    }
}
