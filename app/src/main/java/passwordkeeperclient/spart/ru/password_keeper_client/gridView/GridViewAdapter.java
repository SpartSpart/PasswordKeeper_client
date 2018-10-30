package passwordkeeperclient.spart.ru.password_keeper_client.gridView;

import android.content.ContentResolver;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import passwordkeeperclient.spart.ru.password_keeper_client.R;

public class GridViewAdapter extends ArrayAdapter<GridViewModel>{

private ArrayList<GridViewModel> gridViewModels = new ArrayList<>();
private Context context;
private String description;
private String login;
private String password;

    public GridViewAdapter(@NonNull Context context, int resource, ArrayList<GridViewModel> gridViewModels) {
        super(context, resource, gridViewModels);
        this.context = context;
        this.gridViewModels = gridViewModels;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        description = getItem(position).getDescription();
        login = getItem(position).getLogin();
        password = getItem(position).getPassword();
//        if(convertView==null){
//            convertView = LayoutInflaterCompat.from(getContext()).inflate(R.layout.list_item, parent);
//       }
//        TextView myTextView = (TextView) convertView.findViewById(R.id.textView);
//        myTextView.setText(myTask.getTaskText());
//        CheckBox myCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox);
//        myCheckBox.setChecked(myTask.getChecked());

             return super.getView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return gridViewModels.size()*3;
    }

    @Nullable
    @Override
    public GridViewModel getItem(int position) {
        return gridViewModels.get(position);
    }
}
