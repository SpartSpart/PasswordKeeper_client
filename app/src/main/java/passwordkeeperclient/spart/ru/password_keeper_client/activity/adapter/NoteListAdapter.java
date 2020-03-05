package passwordkeeperclient.spart.ru.password_keeper_client.activity.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import passwordkeeperclient.spart.ru.password_keeper_client.R;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.NoteModel;
import passwordkeeperclient.spart.ru.password_keeper_client.api.model.SecretModel;

public class NoteListAdapter extends BaseAdapter implements Filterable {
    Context context;
    LayoutInflater lInflater;
    ArrayList<NoteModel> notes;
    ArrayList<NoteModel> filteredNotes;

    public NoteListAdapter(Context context, ArrayList<NoteModel> notes) {
        this.context = context;
        this.notes = notes;
        filteredNotes = notes;
        lInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return filteredNotes.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredNotes.get(position);
    }

    @Override
    public long getItemId(int position) {return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TextView passwordTxt;
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.note_list_model, parent, false);
        }

        final NoteModel note = getNote(position);

        ((TextView) view.findViewById(R.id.note)).setText(String.valueOf(note.getNote()));

        return view;
    }


    NoteModel getNote(int position) {
        return ((NoteModel) getItem(position));
    }


    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filteredNotes = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filteredNotes.count = notes.size();
                    filteredNotes.values = notes;
                } else {
                    ArrayList<NoteModel> resultsSecrets = new ArrayList<>();
                    String searchValue = constraint.toString().toUpperCase();
                    for (NoteModel noteModel : notes)
                        if (noteModel.getNote().toUpperCase().contains(searchValue))
                            resultsSecrets.add(noteModel);

                    filteredNotes.count = resultsSecrets.size();
                    filteredNotes.values = resultsSecrets;

                }

                return filteredNotes;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredNotes = (ArrayList<NoteModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}