package android.comp3074.project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RecipeAdapter extends ArrayAdapter<String> {
    String[] titles;
    String[] details;
    int[] images;
    Context context;

    public RecipeAdapter(Context context, String[] titles, String[] details, int[] images) {
        super(context, R.layout.listview_item);
        this.titles = titles;
        this.details = details;
        this.images = images;
        this.context = context;
    }

    public int getCount(){
        return titles.length;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
            viewHolder.imgRecipe = (ImageView)convertView.findViewById(R.id.imgRecipe);
            viewHolder.txtTitle = (TextView)convertView.findViewById(R.id.tvRecipeTitle);
            viewHolder.txtDetail = (TextView)convertView.findViewById(R.id.tvRecipeDetail);

            viewHolder.imgRecipe.setImageResource(images[position]);
            viewHolder.txtTitle.setText(titles[position]);
            viewHolder.txtDetail.setText(details[position]);
            convertView.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.imgRecipe.setImageResource(images[position]);
        viewHolder.txtTitle.setText(titles[position]);
        viewHolder.txtDetail.setText(details[position]);

        return convertView;
    }

    static class ViewHolder{
        ImageView imgRecipe;
        TextView txtTitle;
        TextView txtDetail;
    }
}
