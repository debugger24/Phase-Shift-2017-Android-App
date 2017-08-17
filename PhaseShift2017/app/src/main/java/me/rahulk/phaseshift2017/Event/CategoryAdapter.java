package me.rahulk.phaseshift2017.Event;

import android.app.Activity;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.rahulk.phaseshift2017.R;

/**
 * Created by debugger24 on 12/08/17.
 */

public class CategoryAdapter extends ArrayAdapter<Category> {

    public CategoryAdapter(Activity context, List<Category> androidFlavors) {
        super(context, 0, androidFlavors);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Category category = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_category, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        imageView.setImageResource(category.categoryImage);

        TextView versionNameView = (TextView) convertView.findViewById(R.id.txtCategoryTitle);
        versionNameView.setText(category.categoryTitle);

        TextView versionNumberView = (TextView) convertView.findViewById(R.id.txtCategoryDesc);
        versionNumberView.setText(category.categoryDesc);
        return convertView;
    }
}
