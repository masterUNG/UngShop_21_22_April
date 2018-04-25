package masterung.androidthai.in.th.ungshop.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import masterung.androidthai.in.th.ungshop.R;

public class FoodAdapter extends BaseAdapter {

    private Context context;
    private String[] imageStrings, nameFoodStrings, priceStrings, descriptionStrings;

    public FoodAdapter(Context context,
                       String[] imageStrings,
                       String[] nameFoodStrings,
                       String[] priceStrings,
                       String[] descriptionStrings) {
        this.context = context;
        this.imageStrings = imageStrings;
        this.nameFoodStrings = nameFoodStrings;
        this.priceStrings = priceStrings;
        this.descriptionStrings = descriptionStrings;
    }

    @Override
    public int getCount() {
        return nameFoodStrings.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.listview_food, parent, false);

        ImageView imageView = view.findViewById(R.id.imvFood);
        TextView nameFoodTextView = view.findViewById(R.id.txtFoodName);
        TextView priceTextView = view.findViewById(R.id.txtPrice);
        TextView descriptionTextView = view.findViewById(R.id.txtDescription);

//        Show Text
        nameFoodTextView.setText(nameFoodStrings[position]);
        priceTextView.setText(priceStrings[position] + " THB.");
        descriptionTextView.setText(descriptionStrings[position]);

//        Show Image
        Picasso.get().load(imageStrings[position]).into(imageView);


        return view;
    }
}
