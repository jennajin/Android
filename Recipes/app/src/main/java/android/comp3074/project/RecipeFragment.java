package android.comp3074.project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class RecipeFragment extends Fragment {
    private RecipeAdapter adapter;
    private ListView list;
    private ArrayAdapter titleAdapter;
    private String[] recipeTitle = {"Rib-Eye Steak", "Shrimp Scampi", "French Toast", "Casserole", "Garlic Chicken", "Cranberry Sauce",
            "Beef Stew", "Cabbage Rolls", "Lasagna", "Goulash Supreme"};
    private String[] recipeDetail = {
            "1. Whisk together the water, bourbon whiskey, soy sauce, brown sugar, Worcestershire sauce, and lemon juice in a bowl, and pour into a plastic zipper bag. Add the rib-eye steaks, coat with the marinade, squeeze out excess air, and seal the bag. Marinate in the refrigerator for 8 hours or overnight. " +
                    "\n2. Preheat an outdoor grill for high heat, and lightly oil the grate." +
                    "\n3. Remove the rib-eye steaks from the marinade, and shake off excess. Discard the remaining marinade. Grill the steaks on high, 1 to 2 minutes per side, to sear the meat. Move the steaks to a cooler part of the grill and cook for an additional 2 to 3 minutes per side, if desired.",
            "1. Bring a large pot of salted water to a boil. Stir in pasta and return pot to boil. Cook until al dente. Drain well." +
                    "\n2. Melt butter in a large saucepan over medium heat. Stir in garlic and shrimp. Cook, stirring constantly, for 3 to 5 minutes." +
                    "\n3. Stir in wine and pepper. Bring to a boil and cook for 30 seconds while stirring constantly." +
                    "\n4. Mix shrimp with drained pasta in a serving bowl. Sprinkle with cheese and parsley. Serve immediately.",
            "1. Measure flour into a large mixing bowl. Slowly whisk in the milk. Whisk in the salt, eggs, cinnamon, vanilla extract and sugar until smooth." +
                    "\n2. Heat a lightly oiled griddle or frying pan over medium heat." +
                    "\n3. Soak bread slices in mixture until saturated. Cook bread on each side until golden brown. Serve hot.",
            "1. Preheat oven to 375 degrees F (190 degrees C). Lightly grease a 9x13 inch square baking dish." +
                    "\n2. Place sausage in a large, deep skillet. Cook over medium-high heat until evenly brown. Drain, crumble, and set aside." +
                    "\n3. In the prepared baking dish, stir together the shredded potatoes and butter. Line the bottom and sides of the baking dish with the mixture. In a bowl, mix the sausage, Cheddar cheese, onion, cottage cheese, and eggs. Pour over the potato mixture." +
                    "\n4. Bake 1 hour in the preheated oven, or until a toothpick inserted into center of the casserole comes out clean. Let cool for 5 minutes before serving.",
            "1. Heat olive oil and garlic in a small saucepan over low heat until warmed, 1 to 2 minutes. Transfer garlic and oil to a shallow bowl." +
                    "\n2. Combine bread crumbs and Parmesan cheese in a separate shallow bowl." +
                    "\n3. Dip chicken breasts in the olive oil-garlic mixture using tongs; transfer to bread crumb mixture and turn to evenly coat. Transfer coated chicken to a shallow baking dish." +
                    "\n4. Bake in the preheated oven until no longer pink and juices run clear, 30 to 35 minutes. An instant-read thermometer inserted into the center should read at least 165 degrees.",
            "1. In a medium sized saucepan over medium heat, dissolve the sugar in the orange juice. Stir in the cranberries and cook until the cranberries start to pop (about 10 minutes). Remove from heat and place sauce in a bowl. Cranberry sauce will thicken as it cools.",
            "1. In a large pot or dutch oven, cook beef in oil over medium heat until brown. Dissolve bouillon in water and pour into pot. Stir in rosemary, parsley and pepper. Bring to a boil, then reduce heat, cover and simmer 1 hour." +
                    "\n2. Stir potatoes, carrots, celery, and onion into the pot. Dissolve cornstarch in 2 teaspoons cold water and stir into stew. Cover and simmer 1 hour more.",
            "1. In a medium saucepan, bring water to a boil. Add rice and stir. Reduce heat, cover and simmer for 20 minutes." +
                    "\n2. Bring a large, wide saucepan of lightly salted water to a boil. Add cabbage leaves and cook for 2 to 4 minutes or until softened; drain." +
                    "\n3. In a medium mixing bowl, combine the ground beef, 1 cup cooked rice, onion, egg, salt and pepper, along with 2 tablespoons of tomato soup. Mix thoroughly.",
            "1. In a skillet over medium heat, brown ground beef, onion and garlic for 5 minutes; drain fat. Mix in basil, oregano, brown sugar, 1 1/2 teaspoons salt, diced tomatoes and tomato paste. Simmer for 30 to 45 minutes, stirring occasionally." +
                    "\n2. Layer 1/3 of the lasagna noodles in the bottom of a 9x13 inch baking dish. Cover noodles with 1/2 ricotta mixture, 1/2 of the mozzarella cheese and 1/3 of the sauce. Repeat. Top with remaining noodles and sauce. Sprinkle additional Parmesan cheese over the top." +
                    "\n3. Bake in the preheated oven 30 minutes. Let stand 10 minutes before serving.",
            "1. In large saucepan brown ground chuck, drain." +
                    "\n2. Add tomatoes, onions, garlic, paprika, chili powder, macaroni and tomato paste, if desired. Add water, a tablespoon at a time, if mixture seems too dry. Simmer until macaroni is tender."};
    private int[] recipeImg = {R.drawable.recipe_steak,
        R.drawable.recipe_shrimp,
        R.drawable.recipe_french_toast,
        R.drawable.recipe_casserole,
        R.drawable.recipe_garlic_chicken,
        R.drawable.recipe_cranberry,
        R.drawable.recipe_stew,
        R.drawable.recipe_cabbage,
        R.drawable.recipe_lasagna,
        R.drawable.recipe_goulash_supreme};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe, container, false);

        list = (ListView)v.findViewById(R.id.lvRecipe);

        adapter = new RecipeAdapter(getActivity(), recipeTitle, recipeDetail, recipeImg);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecipeDetailFragment fragment = new RecipeDetailFragment();
                Bundle bundle = new Bundle();

                bundle.putString("TITLE", recipeTitle[position]);
                bundle.putString("DETAIL", recipeDetail[position]);
                bundle.putInt("IMG", recipeImg[position]);

                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });

        return v;
    }
}
