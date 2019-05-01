package android.comp3074.project;

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

public class ForumDetailsFragment extends Fragment {
    private ForumDatabaseHelper db;
    private EditText forumTitle, forumName, forumDetail, forumDates;
    private Button editForum, deleteForum, backtoList, backtoForum;
    private String forumId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_forum_details, container, false);

        db = new ForumDatabaseHelper(getActivity());
        forumName = v.findViewById(R.id.etForumDetailName);
        forumTitle = v.findViewById(R.id.etForumDetailsTitle);
        forumDetail = v.findViewById(R.id.etForumDetailsDetail);
        forumDates = v.findViewById(R.id.etForumDetailsDate);
        editForum = v.findViewById(R.id.btnEditForum);
        deleteForum = v.findViewById(R.id.btnDeleteForum);
        backtoList = v.findViewById(R.id.btnBackToList);
        backtoForum = v.findViewById(R.id.btnBackToForum2);

        // Get selected data from list
        Bundle bundle = getArguments();
        String selectedName = bundle.getString("NAME");
        String selectedTitle = bundle.getString("TITLE");
        String selectedDetails = bundle.getString("DETAILS");
        String selectedDates = bundle.getString("DATES");
        forumId = bundle.getString("ID");

        forumName.setText(selectedName);
        forumTitle.setText(selectedTitle);
        forumDetail.setText(selectedDetails);
        forumDates.setText(selectedDates);

        // Edit a forum
        editForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = forumTitle.getText().toString();
                String detail = forumDetail.getText().toString();

                if (title.equals("") || detail.equals("")) {
                    if (title.equals(""))
                        forumTitle.setError("Title is required");
                    if (detail.equals(""))
                        forumDetail.setError("Detail is required");

                } else {
                    if (db.editData(forumId, title, detail)) {
                        Toast.makeText(getActivity().getApplicationContext(), "Edited successfully!", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Delete a forum
        deleteForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.deleteData(forumId)) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ForumViewFragment()).commit();
                    Toast.makeText(getActivity().getApplicationContext(), "Deleted successfully!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

        // Back to List
        backtoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ForumViewFragment()).commit();
            }
        });

        // Back to Forum
        backtoForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ForumFragment()).commit();
            }
        });

        return v;
    }
}
