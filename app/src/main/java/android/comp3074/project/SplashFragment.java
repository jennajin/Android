package android.comp3074.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SplashFragment extends Fragment {
    private TextView username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_splash, container, false);

        username = (TextView)v.findViewById(R.id.tvUsername);

        // Set user name with welcome message
        Bundle bundle = getArguments();
        String name = bundle.getString("NAME");
        username.setText("Welcome, " + name +"!");

        return v;
    }
}
