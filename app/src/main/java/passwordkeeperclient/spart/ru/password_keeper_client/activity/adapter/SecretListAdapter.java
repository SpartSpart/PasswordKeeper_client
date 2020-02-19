package passwordkeeperclient.spart.ru.password_keeper_client.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import passwordkeeperclient.spart.ru.password_keeper_client.R;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;

public class SecretListAdapter extends BaseAdapter implements Filterable {
    private static final String HIDDEN_PASSWORD_STRING ="********";
    Context context;
    LayoutInflater lInflater;
    ArrayList<SecretModel> secrets;
    ArrayList<SecretModel> filteredSecrets;
    ImageView passwordVisibleImg;

    public SecretListAdapter(Context context, ArrayList<SecretModel> secrets) {
        this.context = context;
        this.secrets = secrets;
        filteredSecrets = secrets;
        lInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return filteredSecrets.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredSecrets.get(position);
    }

    @Override
    public long getItemId(int position) {return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TextView passwordTxt;
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.secret_list_model, parent, false);
        }

        final SecretModel secret = getSecret(position);

        ((TextView) view.findViewById(R.id.secretDescription)).setText(String.valueOf(secret.getDescription()));
        ((TextView) view.findViewById(R.id.secretLogin)).setText(secret.getLogin());

        passwordTxt = view.findViewById(R.id.secretPassword);
        passwordTxt.setText(HIDDEN_PASSWORD_STRING);
        passwordVisibleImg = view.findViewById(R.id.passwordVisibleImg);
        passwordVisibleImg.setOnClickListener(new PasswordVisible(passwordTxt,secret));

        return view;
    }


    public class PasswordVisible implements OnClickListener{

        private TextView passwordTxt;
        private SecretModel secretModel;

        public PasswordVisible(TextView passwordTxt, SecretModel secretModel) {
            this.passwordTxt = passwordTxt;
            this.secretModel = secretModel;
        }

        @Override
        public void onClick(View view) {
            if (passwordTxt.getText().equals(HIDDEN_PASSWORD_STRING)) {
                passwordTxt.setText(secretModel.getPassword());
            }
            else
                passwordTxt.setText(HIDDEN_PASSWORD_STRING);
        }
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
                    filteredSecrets.count = secrets.size();
                    filteredSecrets.values = secrets;
                } else {
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