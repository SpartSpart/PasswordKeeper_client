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
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.FileModel;

/**
 * Created by Pamela on 19.02.2020.
 */

public class FileListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater lInflater;
    ArrayList<FileModel> files;

    public FileListAdapter(Context context, ArrayList<FileModel> files) {
        this.context = context;
        this.files = files;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.file_list_model, parent, false);
        }

        final FileModel file = getDoc(position);

        ((TextView) view.findViewById(R.id.fileInfo)).setText(String.valueOf(file.getFileName()));

        return view;
    }

    FileModel getDoc(int position) {
        return ((FileModel) getItem(position));
    }
}

//    @Override
//    public Filter getFilter() {
//
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                FilterResults filteredDocs = new FilterResults();
//                if (constraint == null || constraint.length() == 0) {
//                    filteredDocs.count = files.size();
//                    filteredDocs.values = files;
//                } else {
//                    ArrayList<DocModel> resultsDocs = new ArrayList<>();
//                    String searchValue = constraint.toString().toUpperCase();
//                    for (DocModel docModel : files)
//                        if (docModel.getDocument().toUpperCase().contains(searchValue))
//                            resultsDocs.add(docModel);
//
//                    filteredDocs.count = resultsDocs.size();
//                    filteredDocs.values = resultsDocs;
//                }
//
//                return filteredDocs;
//            }

//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                filteredFiles = (ArrayList<DocModel>) results.values;
//                notifyDataSetChanged();
//
//            }
//        };

