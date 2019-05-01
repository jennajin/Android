package android.comp3074.project;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class ForumViewFragment extends Fragment {
    private ForumDatabaseHelper db;
    private ListView listview;
    private Button back;
    private ArrayAdapter listAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_forum_view, container, false);

        listview = v.findViewById(R.id.lvForum);
        back = v.findViewById(R.id.btnBackToForum);
        db = new ForumDatabaseHelper(getActivity());
        EditText search = (EditText)v.findViewById(R.id.searchForum);

        // Create an Array list of table records
        ArrayList<String> tableRecordsList = new ArrayList<>();

        // get all data from the table
        Cursor reterivedTableRecords = db.getData();

        if (reterivedTableRecords.getCount() == 0){
            Toast.makeText(getActivity().getApplicationContext(), "Table is empty", Toast.LENGTH_LONG).show();
        }else{
            while(reterivedTableRecords.moveToNext()){
                tableRecordsList.add(reterivedTableRecords.getString(2));

                listAdapter = new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_list_item_1,  tableRecordsList);

                listview.setAdapter(listAdapter);

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String title = parent.getItemAtPosition(position).toString();

                        Cursor cursor = db.getDataByTitle(title);
                        if (cursor.getCount() > 0){
                            ForumDetailsFragment fragment = new ForumDetailsFragment();
                            Bundle bundle = new Bundle();


                            while (cursor.moveToNext()){
                                bundle.putString("ID", cursor.getString(0));
                                bundle.putString("NAME", cursor.getString(1));
                                bundle.putString("TITLE", cursor.getString(2));
                                bundle.putString("DETAILS", cursor.getString(3));
                                bundle.putString("DATES", cursor.getString(4));

                                fragment.setArguments(bundle);
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                            }
                        };

                    }
                });
            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ForumFragment()).commit();
            }
        });

        // Search recipes
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listAdapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }
}
