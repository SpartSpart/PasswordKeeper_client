package passwordkeeperclient.spart.ru.password_keeper_client.activity.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import passwordkeeperclient.spart.ru.password_keeper_client.CustomClickListener;
import passwordkeeperclient.spart.ru.password_keeper_client.R;
import passwordkeeperclient.spart.ru.password_keeper_client.activity.EditDocActivity;
import passwordkeeperclient.spart.ru.password_keeper_client.activity.fragment.EditFileNameDialog;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.FileModel;

/**
 * Created by Pamela on 19.02.2020.
 */

public class FileListAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {
    Context context;
    LayoutInflater lInflater;
    ArrayList<FileModel> files;
    public SparseBooleanArray selectedItems;
    CheckBox fileCheckBox;
    private ImageButton editFileName;
    private EditDocActivity editDocActivity;



    public FileListAdapter(Context context, ArrayList<FileModel> files) {
        this.context = context;
        this.files = files;
        setSelectedItems();
        lInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public Object getItem(int position) {
        return files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.file_list_model, parent, false);
        }

        final FileModel file = getFile(position);

        ((TextView) view.findViewById(R.id.fileInfo)).setText(String.valueOf(file.getFileName()));
        fileCheckBox = view.findViewById(R.id.fileCheckBox);
        fileCheckBox.setTag(position);
        fileCheckBox.setChecked(selectedItems.get(position, false));
        fileCheckBox.setOnCheckedChangeListener(this);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckedChanged(fileCheckBox,isChecked(position));

            }
        });
        editFileName = view.findViewById(R.id.editFileName);

       editFileName.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v){
               EditDocActivity editDocActivity = (EditDocActivity)context;
               editDocActivity.getEditFileNameFragment(file,position);
           }
       });


        return view;
    }



    FileModel getFile(int position) {
        return ((FileModel) getItem(position));
    }

    public boolean isChecked(int position) {
        return selectedItems.get(position, false);
    }

    public void setChecked(int position, boolean isChecked) {
        selectedItems.put(position, isChecked);
        setVisibleDeleteAndDownloadButtons();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int position = (Integer) buttonView.getTag();
        setChecked(position,isChecked);

    }

    private void setVisibleDeleteAndDownloadButtons(){
        for (int i = 0; i<selectedItems.size(); i++)

            if (selectedItems.valueAt(i) == true){

                EditDocActivity.deleteFilesBtn.setVisibility(View.VISIBLE);
                EditDocActivity.downloadFilesBtn.setVisibility(View.VISIBLE);
                return;
            }
        EditDocActivity.deleteFilesBtn.setVisibility(View.INVISIBLE);
        EditDocActivity.downloadFilesBtn.setVisibility(View.INVISIBLE);
        EditDocActivity.checkAllFilesChkBox.setChecked(false);

    }

    public void setSelectedItems() {
        selectedItems = new SparseBooleanArray(files.size());
    }



}
