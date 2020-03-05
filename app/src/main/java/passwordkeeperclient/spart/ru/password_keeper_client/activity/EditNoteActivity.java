package passwordkeeperclient.spart.ru.password_keeper_client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

import passwordkeeperclient.spart.ru.password_keeper_client.R;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.NoteModel;
import passwordkeeperclient.spart.ru.password_keeper_client.cryptography.CryptText;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.notes.AddNote;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.notes.UpdateNote;

public class EditNoteActivity extends AppCompatActivity {

    private EditText note;

    private NoteModel noteModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        Toolbar toolbar = findViewById(R.id.editToolbar);
        setSupportActionBar(toolbar);

        note = findViewById(R.id.note);

        noteModel = (NoteModel) getIntent().getSerializableExtra("NoteModel");
        if (noteModel != null) {
            note.setText(noteModel.getNote());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_secret_or_note, menu);
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

                    NoteModel note = getNote(
                            this.note.getText().toString()
                    );
                    saveNote(note);
                } catch (Exception e) {
                    Toast.makeText(this, "Save: " + e.toString(), Toast.LENGTH_SHORT).show();
                }
                    return true;
                }
            }
            return super.onOptionsItemSelected(item);
        }


    private void saveNote(NoteModel noteModel) throws ExecutionException, InterruptedException {

        if (noteModel != null) {
            NoteModel newNoteModel = sendNoteToDB(noteModel);
            Intent intent = new Intent();
            intent.putExtra("NoteModelReturn", newNoteModel);
            setResult(1, intent);
            finish();
        }
    }

    private NoteModel getNote(String note) throws ParseException {

        try {
            if (!note.equals("")){

                NoteModel noteModelLocal = noteModel;

                if (noteModelLocal != null) {
                    noteModelLocal = new NoteModel(noteModel.getId(), note);
                } else {
                    noteModelLocal = new NoteModel(note);
                }
                return noteModelLocal;

            } else Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "NoteActivity: "+e.toString(), Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    private NoteModel sendNoteToDB(NoteModel noteModel) {

        Long id = noteModel.getId();

        try {
            NoteModel encryptedModel = encryptNote(noteModel);
            if (id == 0) {
                AddNote addNote = new AddNote(encryptedModel);
                id = addNote.execute().get();
                noteModel.setId(id); //если новая запись, то нужно чтобы в листе отобразилась запись с id, иначе при редактировании -  создастся новая
            } else {
                UpdateNote updateNote = new UpdateNote(noteModel.getId(), encryptedModel);
                updateNote.execute();
            }

        } catch (Exception e) {
            Toast.makeText(this, "sendSecretToDB: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
        return noteModel;
    }

    private NoteModel encryptNote(NoteModel noteModel) throws Exception {
        NoteModel cryptedNote = noteModel;

        cryptedNote.setId(noteModel.getId());
        cryptedNote.setNote(CryptText.encryptString(noteModel.getNote()));

        return cryptedNote;
    }

}
