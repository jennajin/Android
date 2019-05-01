package android.comp3074.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class RecipeDetailFragment extends Fragment {
    private ImageView imgRecipe;
    private TextView txtTitle, txtDetail;
    private Button backtoRecipe, rate;
    private RatingBar ratingBar;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        imgRecipe = v.findViewById(R.id.imgRecipeDetail);
        txtTitle = v.findViewById(R.id.txtRecipeTitle);
        txtDetail = v.findViewById(R.id.txtRecipeDetail);
        backtoRecipe = v.findViewById(R.id.btnBackToRecipe);
        ratingBar = v.findViewById(R.id.ratingBar);
        rate = v.findViewById(R.id.btnRate);

        // Get selected data from list
        Bundle bundle = getArguments();
        int selectedImg = bundle.getInt("IMG");
        String selectedTitle = bundle.getString("TITLE");
        String selectedDetail = bundle.getString("DETAIL");

        imgRecipe.setImageResource(selectedImg);
        txtTitle.setText(selectedTitle);
        txtDetail.setText(selectedDetail);

        // Back to Recipe
        backtoRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecipeFragment()).commit();
            }
        });

        // Rating submit
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Your rating is " + (int)ratingBar.getRating(), Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}
