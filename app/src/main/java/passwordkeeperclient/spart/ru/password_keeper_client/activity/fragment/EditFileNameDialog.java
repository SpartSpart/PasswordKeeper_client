package passwordkeeperclient.spart.ru.password_keeper_client.activity.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import passwordkeeperclient.spart.ru.password_keeper_client.R;
import passwordkeeperclient.spart.ru.password_keeper_client.activity.DocActivity;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.FileModel;
import passwordkeeperclient.spart.ru.password_keeper_client.requests.files.UpdateFileName;

/**
 * Created by Pamela on 01.03.2020.
 */

public class EditFileNameDialog extends DialogFragment {
    private String fileName;
    private long file_id;
    private int position;

    EditText editText;

    @SuppressLint("ValidFragment")
    public EditFileNameDialog(FileModel fileModel, int position) {
        this.fileName = fileModel.getFileName();
        this.file_id = fileModel.getId();
        this.position = position;
    }

    public EditFileNameDialog() {
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_edit_file_name,null);

        editText = (EditText) view.findViewById(R.id.fileName);
        editText.setText(String.valueOf(fileName));

        builder.setView(view)
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String newFileName = editText.getText().toString();
                        mListener.onDialogPositiveClick(EditFileNameDialog.this, newFileName, file_id,position);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(EditFileNameDialog.this);
                    }
                });


        return builder.create();
    }

    NoticeDialogListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String newFileName, long file_id, int position);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
}
