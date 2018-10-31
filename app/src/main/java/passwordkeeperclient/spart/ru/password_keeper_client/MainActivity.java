package passwordkeeperclient.spart.ru.password_keeper_client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import passwordkeeperclient.spart.ru.password_keeper_client.gridView.ListViewAdapter;
import passwordkeeperclient.spart.ru.password_keeper_client.gridView.ListViewModel;

public class MainActivity extends AppCompatActivity {
    private ListView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        mainView = findViewById(R.id.secretList);
        testPushGrid();
    }

    public void testPushGrid(){
        ArrayList<ListViewModel> listViewModels = new ArrayList<>();

        for (int i=0;i<100;i++){
            String s = String.valueOf(i);
            listViewModels.add(new ListViewModel(s,s,s,i));
        }

        ListViewAdapter adapter = new ListViewAdapter(getApplicationContext(),0, listViewModels);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
