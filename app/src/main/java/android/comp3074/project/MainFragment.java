package android.comp3074.project;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainFragment extends Fragment {
    private ImageView imgLogo;
    private TextView txtLogo;
    private UserDatabaseHelper db;
    private String name;
    SplashFragment fragment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        imgLogo = (ImageView)v.findViewById(R.id.imgLogo);
        txtLogo = (TextView)v.findViewById(R.id.txtLogo);
        db = new UserDatabaseHelper(getActivity());

        // Get email from previous page
        String email = getActivity().getIntent().getExtras().getString("EMAIL");


        // Get user information from database
        Cursor cursor = db.getUser(email);

        if( cursor != null && cursor.moveToFirst() ){
            name = cursor.getString(2);
            cursor.close();
        }

        // Set user name
        fragment = new SplashFragment();
        Bundle bundle = new Bundle();
        bundle.putString("NAME", name);
        fragment.setArguments(bundle);


        // Animation
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.transition);
        imgLogo.startAnimation(animation);
        txtLogo.startAnimation(animation);

        // Splash page
        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(1500);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                }
            }
        };
        thread.start();


        return v;
    }
}
