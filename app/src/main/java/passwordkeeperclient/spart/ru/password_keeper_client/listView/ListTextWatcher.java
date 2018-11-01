package passwordkeeperclient.spart.ru.password_keeper_client.listView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import passwordkeeperclient.spart.ru.password_keeper_client.MainActivity;

public class ListTextWatcher implements TextWatcher {

    private ListViewModel listViewModel;

    public ListTextWatcher(ListViewModel listViewModel) {
        this.listViewModel = listViewModel;

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        listViewModel.setDescription(s.toString());
        MainActivity.changedID.add(listViewModel.getId());
   }
}
