package passwordkeeperclient.spart.ru.password_keeper_client.listView;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;

import passwordkeeperclient.spart.ru.password_keeper_client.R;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.Toast;


public class ListViewAdapter extends ArrayAdapter<ListViewModel>{

private ArrayList<ListViewModel> listViewModels;
private Context context;
private ListViewModel listViewModel;
private EditText description;
private EditText login;
private EditText password;

    private ArrayList<ListViewModel> testViewModels = new ArrayList<>();
    private ArrayList <Long> userID = new ArrayList<>();

    public ListViewAdapter(@NonNull Context context, int resource, ArrayList<ListViewModel> listViewModels) {
        super(context, resource, listViewModels);
        this.context = context;
        this.listViewModels = listViewModels;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        listViewModel = listViewModels.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_view_model, null);

        description = view.findViewById(R.id.description);
        login = view.findViewById(R.id.login);
        password = view.findViewById(R.id.password);

        description.setText(listViewModel.getDescription());
        login.setText(listViewModel.getLogin());
        password.setText(listViewModel.getPassword());

        description.addTextChangedListener(new ListTextWatcher(listViewModel));
        login.addTextChangedListener(new ListTextWatcher(listViewModel));
        password.addTextChangedListener(new ListTextWatcher(listViewModel));

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