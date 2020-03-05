package passwordkeeperclient.spart.ru.password_keeper_client.activity.additional;

import android.text.Editable;
import android.text.TextWatcher;

import passwordkeeperclient.spart.ru.password_keeper_client.activity.adapter.DocListAdapter;
import passwordkeeperclient.spart.ru.password_keeper_client.activity.adapter.NoteListAdapter;
import passwordkeeperclient.spart.ru.password_keeper_client.activity.adapter.SecretListAdapter;

/**
 * Created by Pamela on 24.11.2019.
 */

public class SearchDataWatcher implements TextWatcher {
    private SecretListAdapter secretListAdapter;
    private DocListAdapter docListAdapter;
    private NoteListAdapter noteListAdapter;

    public SearchDataWatcher(SecretListAdapter secretListAdapter) {
        this.secretListAdapter = secretListAdapter;
    }

    public SearchDataWatcher(DocListAdapter docListAdapter) {
        this.docListAdapter = docListAdapter;
    }

    public SearchDataWatcher(NoteListAdapter noteListAdapter) {
        this.noteListAdapter = noteListAdapter;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (secretListAdapter!=null)
            secretListAdapter.getFilter().filter(s);
        else
            if (docListAdapter!=null)
            docListAdapter.getFilter().filter(s);
        else
            noteListAdapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
