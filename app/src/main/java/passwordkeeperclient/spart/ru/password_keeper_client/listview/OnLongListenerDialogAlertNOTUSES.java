package passwordkeeperclient.spart.ru.password_keeper_client.listview;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import passwordkeeperclient.spart.ru.password_keeper_client.listview.model.ListViewModel;

/**
 * Created by Pamela on 15.11.2018.
 */

public class OnLongListenerDialogAlertNOTUSES implements View.OnLongClickListener {

    ListViewModel listViewModel;
    Context context;

    public OnLongListenerDialogAlertNOTUSES(ListViewModel listViewModel, Context context) {
        this.listViewModel = listViewModel;
        this.context = context;
    }



    @Override
    public boolean onLongClick(View v) {
        setSecretDialog("Edit","Message","description","login","password");
        return true;
    }


    void setSecretDialog(String title,
                         String message,
                         final String description,
                         final String login,
                         final String password) {



        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        dialog.setTitle(title);
        dialog.setMessage(message);

// Set an EditText view to get user input
        final EditText descriptionText = new EditText(context);
        final EditText loginText = new EditText(context);
        final EditText passwordText = new EditText(context);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);


        descriptionText.setText(description);
        loginText.setText(login);
        passwordText.setText(password);

        descriptionText.setHint("Description");
        loginText.setHint("Login");
        passwordText.setHint("Password");

        layout.addView(descriptionText);
        layout.addView(loginText);
        layout.addView(passwordText);

        dialog.setView(layout);

        dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

//                      descriptionText.getText().toString(),
//                        loginText.getText().toString(),
//                        passwordText.getText().toString());
//                try {
//                    Long id = addSecret.execute().get();
//                    if (id != null) {
//                        listViewModels.add(new ListViewModel(id,
//                                descriptionText.getText().toString(),
//                                loginText.getText().toString(),
//                                passwordText.getText().toString()));
//                    } else
//                        Toast.makeText(getBaseContext(), "Error: Something was wrong", Toast.LENGTH_LONG).show();
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
//                adapter.notifyDataSetChanged();
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        dialog.show();
    }

}



