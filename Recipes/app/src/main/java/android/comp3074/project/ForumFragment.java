package android.comp3074.project;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForumFragment extends Fragment {
    private ForumDatabaseHelper db;
    private UserDatabaseHelper userdb;
    private Button addForum, viewForum;
    private EditText forumTitle, forumName, forumDetails;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_forum, container, false);

        db = new ForumDatabaseHelper(getActivity());
        userdb = new UserDatabaseHelper(getActivity());

        forumName = (EditText) v.findViewById(R.id.etForumName);
        forumTitle = (EditText) v.findViewById(R.id.etForumTitle);
        forumDetails = (EditText) v.findViewById(R.id.etForumDetails);
        addForum = (Button)v.findViewById(R.id.btnAddForum);
        viewForum = (Button)v.findViewById(R.id.btnViewForum);

        // Get email from previous page
        String email = getActivity().getIntent().getExtras().getString("EMAIL");

        // Get user information from database
        Cursor cursor = userdb.getUser(email);

        if( cursor != null && cursor.moveToFirst() ){
            String name = cursor.getString(2);
            forumName.setText(name);
            cursor.close();
        }


        addForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = forumName.getText().toString();
                String title = forumTitle.getText().toString();
                String details = forumDetails.getText().toString();
                if(title.equals("") || details.equals("")){
                    if(title.equals(""))
                        forumTitle.setError("Title is required");
                    if(details.equals(""))
                        forumDetails.setError("Detail is required");
                } else {
                    if(db.insertData(name, title, details))
                        Toast.makeText(getActivity().getApplicationContext(), "Inserted successfully!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity().getApplicationContext(), "Something went wrong ", Toast.LENGTH_SHORT).show();
                    forumTitle.setText("");
                    forumDetails.setText("");
                }
            }
        });

        viewForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ForumViewFragment()).commit();
            }
        });

        return v;
    }

}
