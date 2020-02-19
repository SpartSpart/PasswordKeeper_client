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

import passwordkeeperclient.spart.ru.password_keeper_client.R;
import passwordkeeperclient.spart.ru.password_keeper_client.activity.adapter.DocListAdapter;
import passwordkeeperclient.spart.ru.password_keeper_client.activity.additional.SearchDataWatcher;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.DocModel;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.documents.DeleteDoc;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.documents.GetDocs;

public class DocActivity extends AppCompatActivity {
    private ListView docListView;
    private ArrayList<DocModel> docListModels;
    private DocListAdapter docListAdapter;
    public static String authorization;
    private boolean newDoc;
    private int selectedListPosition;
    private EditText searchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        docListModels = new ArrayList<>();

        setContentView(R.layout.activity_doc);

        Toolbar toolbar = findViewById(R.id.docToolbar);
        setSupportActionBar(toolbar);

        searchData = findViewById(R.id.searchData);

        authorization = getIntent().getStringExtra("Authorization");

        selectedListPosition = 0;

        fillDocList();

        docListViewCreate();

        SearchDataWatcher searchDataWatcher =new SearchDataWatcher(docListAdapter);
        searchData.addTextChangedListener(searchDataWatcher);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshDocListAdapter();
    }

    public void fillDocList() {
        ArrayList<DocModel> docModels = null;

        docModels = new ArrayList<>(getDocs(authorization));

        Long id;
        String document;
        String description;

        if (!docModels.isEmpty()) {
            for (DocModel docModel : docModels) {

                id = docModel.getId();
                document = docModel.getDocument();
                description = docModel.getDescription();

                DocModel model = new DocModel(id, document, description);
                docListModels.add(model);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_doc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh: {
                docListModels.clear();
                fillDocList();
                docListAdapter.getFilter().filter(null);
                docListAdapter.notifyDataSetChanged();
                searchData.setText("");
                return true;
            }
            case R.id.action_new: {
                editDoc(null);
                newDoc = true;
                return true;
            }

            case R.id.action_secret:{
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    public Collection<DocModel> getDocs(String authorization) {
        GetDocs getDocs = new GetDocs(authorization);
        try {
            return getDocs.execute().get();
        } catch (Exception e) {
            Toast.makeText(this, "getFilesInfo:" + e.toString(), Toast.LENGTH_LONG).show();
        }
        return null;
    }

    public void editDoc(DocModel docModel) {
        Intent intent = new Intent(getBaseContext(), EditDocActivity.class);
        intent.putExtra("DocModel", docModel);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        DocModel docModel = (DocModel) data.getSerializableExtra("DocModelReturn");
        try {
            if (newDoc)  //проверка на новую запись или обновляемую
                docListModels.add(docModel);
            else {
                docListModels.get(selectedListPosition).setDocument(docModel.getDocument());
                docListModels.get(selectedListPosition).setDescription(docModel.getDescription());
            }
        }
        catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        selectedListPosition = 0;
    }

    private void docListViewCreate() {

        docListAdapter = new DocListAdapter(this, docListModels);

        docListView = findViewById(R.id.docListView);
        docListView.setTextFilterEnabled(true);
        docListView.setFocusable(true);

        docListView.setAdapter(docListAdapter);
        docListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long l) {
                editDoc((DocModel) parent.getItemAtPosition(pos));
                selectedListPosition = pos;
                newDoc = false;
            }
        });
        docListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> docs, View arg1,
                                           int pos, long id) {
                DocModel docModel = (DocModel) docs.getItemAtPosition(pos);
                deleteDialog(docModel.getId(),docModel.getDocument(), pos);
                return true;
            }
        });

    }

    private void deleteDialog(final Long docId, final String doc, final int listViewPosition){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Warning!");
        builder.setMessage("Delete "+doc+"?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                DeleteDoc deleteDoc = new DeleteDoc(authorization, docId);
                deleteDoc.execute();

                docListModels.remove(listViewPosition);
                refreshDocListAdapter();

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

    private void refreshDocListAdapter(){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                docListModels.clear();
                fillDocList();
                docListAdapter.notifyDataSetChanged();
                refreshFilteredSecrets();
            }
        });
    }

    private void refreshFilteredSecrets(){
        docListAdapter.getFilter().filter(searchData.getText());
    }
}
