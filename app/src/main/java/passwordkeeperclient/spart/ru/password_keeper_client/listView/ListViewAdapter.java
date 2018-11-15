package passwordkeeperclient.spart.ru.password_keeper_client.listview;

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
import passwordkeeperclient.spart.ru.password_keeper_client.listview.model.ListViewModel;

import android.view.LayoutInflater;


public class ListViewAdapter extends ArrayAdapter<ListViewModel>{private ArrayList<ListViewModel> listViewModels;
    private Context context;
    private ListViewModel listViewModel;
    private EditText description;
    private EditText login;
    private EditText password;

    public ListViewAdapter(@NonNull Context context, int resource, ArrayList<ListViewModel> listViewModels) {
        super(context, resource, listViewModels);
        this.context = context;
        this.listViewModels = listViewModels;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        listViewModel = getItem(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_view_model, null);

        description = view.findViewById(R.id.description);
        login = view.findViewById(R.id.login);
        password = view.findViewById(R.id.password);

        description.setText(listViewModel.getDescription());
        login.setText(listViewModel.getLogin());
        password.setText(listViewModel.getPassword());

        description.addTextChangedListener(new ListTextWatcher(listViewModel,position,"DESCRIPTION"));
        login.addTextChangedListener(new ListTextWatcher(listViewModel,position,"LOGIN"));
        password.addTextChangedListener(new ListTextWatcher(listViewModel,position,"PASSWORD"));

        description.setOnLongClickListener(new ListOnLongClickListener( listViewModel));
        login.setOnLongClickListener(new ListOnLongClickListener(listViewModel));
        password.setOnLongClickListener(new ListOnLongClickListener(listViewModel));

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
