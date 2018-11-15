package passwordkeeperclient.spart.ru.password_keeper_client.listview;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import passwordkeeperclient.spart.ru.password_keeper_client.listview.model.ListViewModel;
import passwordkeeperclient.spart.ru.password_keeper_client.resonses.AddSecret;


public class ListOnLongClickListener implements View.OnLongClickListener {

    ListViewModel listViewModel;

    public ListOnLongClickListener(ListViewModel listViewModel) {
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



