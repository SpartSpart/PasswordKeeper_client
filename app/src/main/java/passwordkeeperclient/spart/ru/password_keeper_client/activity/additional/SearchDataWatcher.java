package passwordkeeperclient.spart.ru.password_keeper_client.activity.additional;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Pamela on 24.11.2019.
 */

public class SearchDataWatcher implements TextWatcher {
    private SecretListAdapter secretListAdapter;

    public SearchDataWatcher(SecretListAdapter secretListAdapter) {
        this.secretListAdapter = secretListAdapter;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        secretListAdapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
