package passwordkeeperclient.spart.ru.password_keeper_client.listview;

import android.text.Editable;
import android.text.TextWatcher;

import passwordkeeperclient.spart.ru.password_keeper_client.MainActivity;
import passwordkeeperclient.spart.ru.password_keeper_client.listview.model.ListViewModel;

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
