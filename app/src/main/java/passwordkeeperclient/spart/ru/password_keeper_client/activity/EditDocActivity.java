package passwordkeeperclient.spart.ru.password_keeper_client.activity;

import android.content.Intent;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import passwordkeeperclient.spart.ru.password_keeper_client.R;
import passwordkeeperclient.spart.ru.password_keeper_client.activity.adapter.FileListAdapter;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.DocModel;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.FileModel;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.documents.AddDoc;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.documents.GetDocs;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.documents.GetFilesInfo;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.documents.UpdateDoc;

public class EditDocActivity extends AppCompatActivity {

    private ListView fileListView;
    private ArrayList<FileModel> fileListModels;
    private FileListAdapter fileListAdapter;

    private EditText documentEditTxt;
    private EditText descriptionEditTxt;

    private DocModel docModel;

    private String authorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doc);

        Toolbar toolbar = findViewById(R.id.docEditToolbar);
        setSupportActionBar(toolbar);

        documentEditTxt = findViewById(R.id.docName);
        descriptionEditTxt = findViewById(R.id.docDescription);

        authorization = DocActivity.authorization;

        docModel = (DocModel) getIntent().getSerializableExtra("DocModel");
        if (docModel != null) {

            documentEditTxt.setText(docModel.getDocument());
            descriptionEditTxt.setText(docModel.getDescription());

            fillFileList(docModel.getId());
        }

        fileListViewCreate();

    }

    public void fillFileList(long doc_id) {
        ArrayList<FileModel> fileModels = null;

        fileModels = new ArrayList<>(getFilesInfo(authorization, doc_id));

        if (!fileModels.isEmpty()) {
            fileListModels = fileModels;
            }
     }


    public Collection<FileModel> getFilesInfo(String authorization, long doc_id) {
        GetFilesInfo getFilesInfo = new GetFilesInfo(authorization, doc_id);
        try {
            return getFilesInfo.execute().get();
        } catch (Exception e) {
            Toast.makeText(this, "getFilesInfo:" + e.toString(), Toast.LENGTH_LONG).show();
        }
        return null;
    }

    private void fileListViewCreate() {

        fileListAdapter = new FileListAdapter(this, fileListModels);

        fileListView = findViewById(R.id.fileListView);
        fileListView.setTextFilterEnabled(true);
        fileListView.setFocusable(true);

        fileListView.setAdapter(fileListAdapter);
        fileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long l) {
//                editDoc((DocModel) parent.getItemAtPosition(pos));
//                selectedListPosition = pos;
//                newDoc = false;
            }
        });
        fileListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> docs, View arg1,
                                           int pos, long id) {
//                DocModel docModel = (DocModel) docs.getItemAtPosition(pos);
//                deleteDialog(docModel.getId(),docModel.getDocument(), pos);
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_doc, menu);
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

                    DocModel doc = getDoc(
                            documentEditTxt.getText().toString(),
                            descriptionEditTxt.getText().toString()
                    );
                    saveDoc(doc);
                } catch (Exception e) {
                    Toast.makeText(this, "Save: " + e.toString(), Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private void saveDoc(DocModel docModel) throws ExecutionException, InterruptedException {

        if (docModel != null) {
            DocModel newDocModel = sendSecretToDB(docModel);
            Intent intent = new Intent();
            intent.putExtra("DocModelReturn", newDocModel);
            setResult(1, intent);
            finish();
        }
    }

    private DocModel getDoc(String docName, String docDescription) throws ParseException {

        try {
            if (!docName.equals("")){

                DocModel docModelLocal = docModel;

                if (docModelLocal != null) {
                    docModelLocal = new DocModel(docModel.getId(), docName, docDescription);
                } else {
                    docModelLocal = new DocModel(docName, docDescription);
                }
                return docModelLocal;

            } else Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "EditActivity: "+e.toString(), Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    private DocModel sendSecretToDB(DocModel docModel) {

        Long id = docModel.getId();

        try {
            if (id == 0) {
                AddDoc addDoc = new AddDoc(authorization, docModel);
                id = addDoc.execute().get();
                docModel.setId(id); //если новая запись, то нужно чтобы в листе отобразилась запись с id, иначе при редактировании -  создастся новая
            } else {
                UpdateDoc updateDoc = new UpdateDoc(authorization, docModel.getId(), docModel);
                updateDoc.execute();
            }

        } catch (Exception e) {
            Toast.makeText(this, "sendSecretToDB: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
        return docModel;
    }

}