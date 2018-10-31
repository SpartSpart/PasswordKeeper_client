package passwordkeeperclient.spart.ru.password_keeper_client.gridView;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;

import passwordkeeperclient.spart.ru.password_keeper_client.R;
import android.view.LayoutInflater;


public class ListViewAdapter extends ArrayAdapter<ListViewModel>{

private ArrayList<ListViewModel> listViewModels;
private Context context;

    public ListViewAdapter(@NonNull Context context, int resource, ArrayList<ListViewModel> listViewModels) {
        super(context, resource, listViewModels);
        this.context = context;
        this.listViewModels = listViewModels;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ListViewModel listViewModel = listViewModels.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_view_model, null);

        EditText description = view.findViewById(R.id.description);
        EditText login = view.findViewById(R.id.login);
        EditText password = view.findViewById(R.id.password);

        description.setText(listViewModel.getDescription());
        login.setText(listViewModel.getLogin());
        password.setText(listViewModel.getPassword());

        return view;
    }

    @Override
    public int getCount() {
        return listViewModels.size();
    }

    @Nullable
    @Override
    public ListViewModel getItem(int position) {
        return listViewModels.get(position);
    }
}
