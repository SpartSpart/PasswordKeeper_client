package passwordkeeperclient.spart.ru.password_keeper_client.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import passwordkeeperclient.spart.ru.password_keeper_client.R;
import passwordkeeperclient.spart.ru.password_keeper_client.activity.adapter.FileListAdapter;
import passwordkeeperclient.spart.ru.password_keeper_client.activity.fragment.EditFileNameDialog;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.DocModel;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.FileModel;
import passwordkeeperclient.spart.ru.password_keeper_client.cryptography.CryptFile;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.documents.AddDoc;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.files.DeleteFiles;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.files.DownloadFiles;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.files.GetFilesInfo;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.documents.UpdateDoc;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.files.UpdateFileName;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.files.UploadFile;

public class EditDocActivity extends AppCompatActivity implements EditFileNameDialog.NoticeDialogListener
{

    private ListView fileListView;
    private ArrayList<FileModel> fileListModels;
    private FileListAdapter fileListAdapter;

    private EditText documentEditTxt;
    private EditText descriptionEditTxt;

    public static CheckBox checkAllFilesChkBox;

    public static ImageButton deleteFilesBtn;
    public static ImageButton downloadFilesBtn;

    private ImageButton uploadFile;

    private DocModel docModel;

    private MenuItem saveMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doc);

        Toolbar toolbar = findViewById(R.id.docEditToolbar);
        setSupportActionBar(toolbar);

        documentEditTxt = findViewById(R.id.docName);
        descriptionEditTxt = findViewById(R.id.docDescription);

        checkAllFilesChkBox = findViewById(R.id.checkAllCheckBox);

        deleteFilesBtn = findViewById(R.id.deleteFiles);
        downloadFilesBtn = findViewById(R.id.downloadFiles);
        uploadFile = findViewById(R.id.uploadFile);

        fileListModels = new ArrayList<>();

        docModel = (DocModel) getIntent().getSerializableExtra("DocModel");
        if (docModel != null) {

            documentEditTxt.setText(docModel.getDocument());
            descriptionEditTxt.setText(docModel.getDescription());
            fillFileList(docModel.getId());

        }
        fileListViewCreate();

        setEditTextItemsProperties();
        setButtons();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int READ_REQUEST_CODE = 100;
            if (requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK) {

                String fullPath = data.getData().getLastPathSegment();
                String filePath = fullPath.substring(fullPath.lastIndexOf(":") + 1, fullPath.length());
                String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
                String finalFilePath = baseDir + File.separator + filePath;
                String fileName = finalFilePath.substring(finalFilePath.lastIndexOf("/"),finalFilePath.length());

                File file = new File(finalFilePath);

                try {
                    String tempDirPath = baseDir+"/Password-Keeper/Temp"+getDate();
                    File tempDirectory = new File(tempDirPath);
                    if (!tempDirectory.exists())
                        tempDirectory.mkdirs();
                    File tempFile = new File(tempDirPath+fileName);

                    CryptFile.encrypt(file, tempFile);
                    UploadFile uploadFile = new UploadFile(docModel.getId(), tempFile);


                   FileModel downloadedFileModel = uploadFile.execute().get();

                    FileUtils.deleteDirectory(tempDirectory);

                    if (downloadedFileModel != null)
                        fileListModels.add(downloadedFileModel);
                    fileListAdapter.notifyDataSetChanged();
                    checkAllFilesChkBox.setVisibility(View.VISIBLE);
                    checkAllFilesChkBox.setChecked(false);
                    selectAllItems(false);

                } catch (Exception e){
                    Toast.makeText(this, "Upload Failed: "+e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
    }

    private String getDate(){
        String pattern = "yyyy-MM-dd HH-mm-ss";
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(new Date());

        return date;
    }


    private void setButtons() {
        deleteFilesBtn.setVisibility(View.INVISIBLE);
        downloadFilesBtn.setVisibility(View.INVISIBLE);

        checkAllFilesChkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selectAllItems(isChecked);
                descriptionEditTxt.clearFocus();
                documentEditTxt.clearFocus();
                hideKeyboard(EditDocActivity.this);
            }
        });

        deleteFilesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<FileModel> deleteFileList = new ArrayList<>();
                SparseBooleanArray selectedItems = fileListAdapter.selectedItems;
                for(int i=0;i<fileListModels.size();i++) {
                    if (selectedItems.get(i) == true)
                        deleteFileList.add(fileListModels.get(i));
                }
                deleteDialog(deleteFileList);
            }
        });

        downloadFilesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<FileModel> downloadFileList = new ArrayList<>();
                SparseBooleanArray selectedItems = fileListAdapter.selectedItems;
                for(int i=0;i<fileListModels.size();i++) {
                    if (selectedItems.get(i) == true)
                        downloadFileList.add(fileListModels.get(i));
                }
                for (FileModel model : downloadFileList){
                    DownloadFiles downloadFiles = new DownloadFiles(model.getId(),docModel.getDocument(), model.getFileName());
                    try {
                        downloadFiles.execute().get();
                    } catch (InterruptedException e) {
                        Toast.makeText(EditDocActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    } catch (ExecutionException e) {
                        Toast.makeText(EditDocActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                Toast.makeText(EditDocActivity.this, "Files saved successfully", Toast.LENGTH_SHORT).show();
                selectAllItems(false);
            }
        });

        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int READ_REQUEST_CODE = 100;
                Intent intent = new Intent()
                        .setType("*/*")
                        .setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select a file"), READ_REQUEST_CODE);
            }
        });
    }

    private void setEditTextItemsProperties(){
        documentEditTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                saveMenuItem.setVisible(true);
            }
        });

        descriptionEditTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                saveMenuItem.setVisible(true);
            }
        });
    }

     private void selectAllItems(boolean isChecked){
        if (isChecked) {
            for (int i = 0; i < fileListModels.size(); i++)
                fileListAdapter.selectedItems.put(i, true);
        }
        else {
            for (int i = 0; i < fileListModels.size(); i++)
                fileListAdapter.selectedItems.put(i, false);
                checkAllFilesChkBox.setChecked(false);
                downloadFilesBtn.setVisibility(View.INVISIBLE);
                deleteFilesBtn.setVisibility(View.INVISIBLE);
        }
        fileListAdapter.notifyDataSetChanged();
    }

    private void deleteDialog(final List<FileModel> deleteFileList){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Warning!");
        builder.setMessage("Delete selected files?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                List<Long> idFileList = new ArrayList<>();
                for (FileModel file : deleteFileList)
                    idFileList.add(file.getId());

                DeleteFiles deleteFiles = new DeleteFiles(idFileList);
                deleteFiles.execute();

                for (FileModel file : deleteFileList) {
                    fileListModels.remove(file);
                }

               fileListAdapter.setSelectedItems();
                selectAllItems(false);

                if (fileListModels.isEmpty())
                    checkAllFilesChkBox.setVisibility(View.INVISIBLE);

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

    public void fillFileList(long doc_id) {
        ArrayList<FileModel> fileModels = null;

        fileModels = new ArrayList<>(getFilesInfo(doc_id));

        if (!fileModels.isEmpty()) {
            fileListModels = fileModels;
            checkAllFilesChkBox.setVisibility(View.VISIBLE);
            }
     }

    public Collection<FileModel> getFilesInfo(long doc_id) {
        GetFilesInfo getFilesInfo = new GetFilesInfo(doc_id);
        try {
            return getFilesInfo.execute().get();
        } catch (Exception e) {
            Toast.makeText(this, "getFilesInfo: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        return null;
    }

    private void fileListViewCreate() {
        android.support.v4.app.FragmentManager fm = this.getSupportFragmentManager();

        fileListAdapter = new FileListAdapter(this, fileListModels);

        fileListView = findViewById(R.id.fileListView);

        fileListView.setAdapter(fileListAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_doc, menu);
        saveMenuItem = menu.findItem(R.id.action_save);
        saveMenuItem.setVisible(false);
        return true;
    }

    @SuppressLint("WrongConstant")
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
        if (isCorrectDocName(documentEditTxt.getText().toString())) {
            if (docModel != null) {
                DocModel newDocModel = sendSecretToDB(docModel);
                Intent intent = new Intent();
                intent.putExtra("DocModelReturn", newDocModel);
                setResult(1, intent);
                finish();

            }
        }
    }

    private boolean isDocNameNotExists(String docName){
        ArrayList<DocModel> docModels = DocActivity.getDocListView();
        if (docModel != null) {
            for (DocModel model : docModels) {
                if (docName.equals(model.getDocument()) &&
                        !docName.equals(docModel.getDocument())){
                    return false;
                }

            }
        }else{
            for (DocModel model : docModels) {
                if (docName.equals(model.getDocument())) {
                   return false;
                }
            }
        }
        return true;
    }

    private boolean isCorrectDocName(String docName) {

        if (isDocNameNotExists(docName)){
            return isCorrectName(docName);
        }
        else{
            Toast.makeText(this, "Name is already exists", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    private boolean isCorrectName(String name) {

        Pattern pattern = Pattern.compile("^[a-zA-ZА-Яа-я0-9_. ]+$");
        Matcher matcher = pattern.matcher(name);

        if(matcher.find())
            return true;
        else
            Toast.makeText(this, "Unacceptable symbols", Toast.LENGTH_SHORT).show();

        return false;
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
                AddDoc addDoc = new AddDoc(docModel);
                id = addDoc.execute().get();
                docModel.setId(id); //если новая запись, то нужно чтобы в листе отобразилась запись с id, иначе при редактировании -  создастся новая
            } else {
                UpdateDoc updateDoc = new UpdateDoc(docModel.getId(), docModel);
                updateDoc.execute();
            }
        } catch (Exception e) {
            Toast.makeText(this, "sendSecretToDB: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
        return docModel;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void getEditFileNameFragment(FileModel fileModel,int position){
        EditFileNameDialog editFileNameDialog = new EditFileNameDialog(fileModel, position);
        editFileNameDialog.show(getSupportFragmentManager(),"fragment");
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String newFileName, long file_id, int position) {

                    if(isCorrectName(newFileName)) {
                        UpdateFileName updateFileName = new UpdateFileName(file_id, newFileName);

                        try {
                            if (updateFileName.execute().get()) {
                                fileListModels.get(position).setFileName(newFileName);
                                fileListAdapter.notifyDataSetChanged();
                            } else if (!newFileName.equals(fileListModels.get(position).getFileName()))
                                Toast.makeText(this, "File already exists", Toast.LENGTH_SHORT).show();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}
