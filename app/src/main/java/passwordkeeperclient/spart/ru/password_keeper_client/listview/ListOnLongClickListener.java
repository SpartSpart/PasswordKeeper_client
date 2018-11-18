package passwordkeeperclient.spart.ru.password_keeper_client.listview;

import android.view.View;
import android.widget.Toast;
import passwordkeeperclient.spart.ru.password_keeper_client.listview.model.ListViewModel;

public class ListOnLongClickListener implements View.OnLongClickListener {

    private ListViewModel listViewModel;

    ListOnLongClickListener(ListViewModel listViewModel) {
        this.listViewModel = listViewModel;

    }


    @Override //first variant of longClick
    public boolean onLongClick(View v) {
        Toast.makeText(v.getContext(),
                listViewModel.getDescription() + "\n" + listViewModel.getLogin() + "\n" + listViewModel.getPassword(),
                Toast.LENGTH_SHORT).show();
        return true;
    }

}



