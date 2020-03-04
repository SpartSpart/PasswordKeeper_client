package passwordkeeperclient.spart.ru.password_keeper_client;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import passwordkeeperclient.spart.ru.password_keeper_client.activity.fragment.EditFileNameDialog;

/**
 * Created by Pamela on 01.03.2020.
 */

public class CustomClickListener extends AppCompatActivity implements View.OnClickListener {

    EditFileNameDialog editFileNameDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        editFileNameDialog = new EditFileNameDialog();
        editFileNameDialog.show(getSupportFragmentManager(),"fragment");

    }
}
