package passwordkeeperclient.spart.ru.password_keeper_client.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import passwordkeeperclient.spart.ru.password_keeper_client.activity.adapter.NoteListAdapter;
import passwordkeeperclient.spart.ru.password_keeper_client.activity.additional.SearchDataWatcher;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.NoteModel;
import passwordkeeperclient.spart.ru.password_keeper_client.credentianals.Principal;
import passwordkeeperclient.spart.ru.password_keeper_client.cryptography.CryptText;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.notes.GetNotes;

public class NoteActivity extends AppCompatActivity {
    private ListView noteListView;
    private ArrayList<NoteModel> noteListModels;
    private NoteListAdapter noteListAdapter;
    private boolean newNote;
    private int selectedListPosition;
    private EditText searchData;

    private DrawerLayout drawer;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noteListModels = new ArrayList<>();

        setContentView(R.layout.activity_note);

        setNavigationMenuProperties();

        searchData = findViewById(R.id.searchData);

        selectedListPosition = 0;

        fillNoteList();

        noteListViewCreate();

        SearchDataWatcher searchDataWatcher =new SearchDataWatcher(noteListAdapter);
        searchData.addTextChangedListener(searchDataWatcher);

    }

    private void setNavigationMenuProperties(){
        Toolbar toolbar = findViewById(R.id.noteToolbar);
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
                          showSecretActivity();
                          break;
                     }
                     case R.id.docs: {
                         showDocActivity();
                         break;
                     }
                     case R.id.notes: {
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
        refreshNoteListAdapter();
        navigationView.setCheckedItem(R.id.notes);
    }

    public void fillNoteList() {
        ArrayList<NoteModel> noteModels = null;

        noteModels = new ArrayList<>(getNotes());

        Long id;
        String note;

        if (!noteModels.isEmpty()) {
            for (NoteModel noteModel : noteModels) {

                id = noteModel.getId();
                note = CryptText.decryptString(noteModel.getNote());

                NoteModel model = new NoteModel(id, note);
                noteListModels.add(model);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
           case R.id.action_refresh: {
                noteListModels.clear();
                fillNoteList();
                noteListAdapter.getFilter().filter(null);
                noteListAdapter.notifyDataSetChanged();
                searchData.setText("");
                return true;
            }
            case R.id.action_new: {
                editNote(null);
                newNote = true;
                return true;
               }
        }
        return super.onOptionsItemSelected(item);
    }


    public Collection<NoteModel> getNotes() {
        GetNotes getNotes = new GetNotes();
        try {
            return getNotes.execute().get();
        } catch (Exception e) {
            Toast.makeText(this, "getNoteInfo:" + e.toString(), Toast.LENGTH_LONG).show();
        }
        return null;
    }

    public void editNote(NoteModel noteModel) {
        Intent intent = new Intent(getBaseContext(), EditNoteActivity.class);
        intent.putExtra("NoteModel", noteModel);
        startActivityForResult(intent,1);
    }

    private void showDocActivity(){
        Intent intent = new Intent(getBaseContext(), DocActivity.class);
        startActivity(intent);
    }

    private void showSecretActivity(){
        Intent intent = new Intent(getBaseContext(), SecretActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        NoteModel noteModel = (NoteModel) data.getSerializableExtra("NoteModelReturn");
        try {
            if (newNote)  //проверка на новую запись или обновляемую
                noteListModels.add(noteModel);
            else {
                  noteListModels.get(selectedListPosition).setNote(noteModel.getNote());
            }
        }
        catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        selectedListPosition = 0;
    }

    private void noteListViewCreate() {

        noteListAdapter = new NoteListAdapter(this, noteListModels);

        noteListView = findViewById(R.id.noteListView);
        noteListView.setTextFilterEnabled(true);
        noteListView.setFocusable(true);

        noteListView.setAdapter(noteListAdapter);
        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long l) {
                editNote((NoteModel) parent.getItemAtPosition(pos));
                selectedListPosition = pos;
                newNote = false;
            }
        });
        noteListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> notes, View arg1,
                                           int pos, long id) {
                NoteModel noteModel = (NoteModel) notes.getItemAtPosition(pos);
                deleteDialog(noteModel.getId(),noteModel.getNote(), pos);
                return true;
            }
        });

    }

    private void deleteDialog(final Long noteId, final String note, final int listViewPosition){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Warning!");
        builder.setMessage("Delete "+note.substring(0,20)+"?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

//                DeleteNote deleteSecret = new DeleteNote(noteId);
//                deleteSecret.execute();
//
//                noteListModels.remove(listViewPosition);
//                refreshNoteListAdapter();

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


    private void refreshNoteListAdapter(){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                noteListModels.clear();
                fillNoteList();
                noteListAdapter.notifyDataSetChanged();
                refreshFilteredSecrets();
            }
        });
    }

    private void refreshFilteredSecrets(){
                noteListAdapter.getFilter().filter(searchData.getText());
        }

}

