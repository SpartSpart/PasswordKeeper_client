package passwordkeeperclient.spart.ru.password_keeper_client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import passwordkeeperclient.spart.ru.password_keeper_client.listView.ListViewAdapter;
import passwordkeeperclient.spart.ru.password_keeper_client.listView.ListViewModel;

public class MainActivity extends AppCompatActivity {
    private ListView mainView;
    static ArrayList<ListViewModel> listViewModels = new ArrayList<>();
    public static Set<Long> changedID = new HashSet<>(); //For saving id of objects that were changed in the listView for further changing in DB



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        changedID.clear();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        mainView = findViewById(R.id.secretList);

        buildListModel();
    }

    public void buildListModel() {

        if (listViewModels.isEmpty()) {

            for (int i = 0; i < 10; i++) {
                String s = String.valueOf(i * 10);
                listViewModels.add(new ListViewModel(i,s, s, s));
            }
            Toast.makeText(this, "First start",
                    Toast.LENGTH_LONG).show();
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
}
