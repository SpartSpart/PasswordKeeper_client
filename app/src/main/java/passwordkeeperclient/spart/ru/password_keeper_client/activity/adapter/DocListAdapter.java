package passwordkeeperclient.spart.ru.password_keeper_client.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import passwordkeeperclient.spart.ru.password_keeper_client.R;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.DocModel;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;

/**
 * Created by Pamela on 19.02.2020.
 */

public class DocListAdapter extends BaseAdapter implements Filterable {
    Context context;
    LayoutInflater lInflater;
    ArrayList<DocModel> docs;
    ArrayList<DocModel> filteredDocs;

    public DocListAdapter(Context context, ArrayList<DocModel> docs) {
        this.context = context;
        this.docs = docs;
        filteredDocs = docs;
        lInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return filteredDocs.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredDocs.get(position);
    }

    @Override
    public long getItemId(int position) {return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TextView passwordTxt;
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.doc_list_model, parent, false);
        }

        final DocModel doc = getDoc(position);

        ((TextView) view.findViewById(R.id.document)).setText(String.valueOf(doc.getDocument()));
        ((TextView) view.findViewById(R.id.description)).setText(doc.getDescription());

        return view;
    }

    DocModel getDoc(int position) {
        return ((DocModel) getItem(position));
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filteredDocs = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filteredDocs.count = docs.size();
                    filteredDocs.values = docs;
                } else {
                    ArrayList<DocModel> resultsDocs = new ArrayList<>();
                    String searchValue = constraint.toString().toUpperCase();
                    for (DocModel docModel : docs)
                        if (docModel.getDocument().toUpperCase().contains(searchValue))
                            resultsDocs.add(docModel);

                    filteredDocs.count = resultsDocs.size();
                    filteredDocs.values = resultsDocs;
                }

                return filteredDocs;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredDocs = (ArrayList<DocModel>) results.values;
                notifyDataSetChanged();

            }
        };
    }
}