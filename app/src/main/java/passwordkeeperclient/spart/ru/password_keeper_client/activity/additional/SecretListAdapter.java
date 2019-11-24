package passwordkeeperclient.spart.ru.password_keeper_client.activity.additional;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import java.util.ArrayList;
import passwordkeeperclient.spart.ru.password_keeper_client.R;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;

public class SecretListAdapter extends BaseAdapter implements Filterable {
    Context context;
    LayoutInflater lInflater;
    ArrayList<SecretModel> secrets;
    ArrayList<SecretModel> filteredSecrets;

    public SecretListAdapter(Context context, ArrayList<SecretModel> secrets) {
        this.context = context;
        this.secrets = secrets;
        filteredSecrets = secrets;
        lInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    // кол-во элементов
    @Override
    public int getCount() {
        return filteredSecrets.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return filteredSecrets.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {return position;}

    // пункт списка

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_view_model, parent, false);
        }

        final SecretModel secret = getSecret(position);

        // заполняем View в пункте списка данными

        ((TextView) view.findViewById(R.id.secretDescription)).setText(String.valueOf(secret.getDescription()));
        ((TextView) view.findViewById(R.id.secretLogin)).setText(secret.getLogin());
        ((TextView) view.findViewById(R.id.secretPassword)).setText(secret.getPassword());


        return view;
    }

    SecretModel getSecret(int position) {
        return ((SecretModel) getItem(position));
    }


    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filteredSecrets = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    //no constraint given, just return all the data. (no search)
                    filteredSecrets.count = secrets.size();
                    filteredSecrets.values = secrets;
                } else {//do the search
                    ArrayList<SecretModel> resultsSecrets = new ArrayList<>();
                    String searchValue = constraint.toString().toUpperCase();
                    for (SecretModel secretModel : secrets)
                        if (secretModel.getDescription().toUpperCase().contains(searchValue))
                            resultsSecrets.add(secretModel);

                    filteredSecrets.count = resultsSecrets.size();
                    filteredSecrets.values = resultsSecrets;

                }

                return filteredSecrets;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredSecrets = (ArrayList<SecretModel>) results.values;
                notifyDataSetChanged();

            }

        };
    }


}