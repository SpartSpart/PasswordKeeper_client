package passwordkeeperclient.spart.ru.password_keeper_client.activity.additional;

import android.text.Editable;
import android.text.TextWatcher;

import passwordkeeperclient.spart.ru.password_keeper_client.activity.adapter.DocListAdapter;
import passwordkeeperclient.spart.ru.password_keeper_client.activity.adapter.SecretListAdapter;

/**
 * Created by Pamela on 24.11.2019.
 */

public class SearchDataWatcher implements TextWatcher {
    private SecretListAdapter secretListAdapter;
    private DocListAdapter docListAdapter;

    public SearchDataWatcher(SecretListAdapter secretListAdapter) {
        this.secretListAdapter = secretListAdapter;
    }

    public SearchDataWatcher(DocListAdapter docListAdapter) {
        this.docListAdapter = docListAdapter;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (secretListAdapter!=null)
            secretListAdapter.getFilter().filter(s);
        else
            docListAdapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
