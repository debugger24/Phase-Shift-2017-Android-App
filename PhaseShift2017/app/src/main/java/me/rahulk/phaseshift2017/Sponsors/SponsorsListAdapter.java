package me.rahulk.phaseshift2017.Sponsors;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.rahulk.phaseshift2017.Newsfeed.FeedItem;
import me.rahulk.phaseshift2017.R;

/**
 * Created by debugger24 on 13/09/17.
 */

public class SponsorsListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<SponsorRow> sponsorRows;

    public SponsorsListAdapter(Activity activity, List<SponsorRow> sponsorRows) {
        this.activity = activity;
        this.sponsorRows = sponsorRows;
    }

    @Override
    public int getCount() {
        return sponsorRows.size();
    }

    @Override
    public Object getItem(int location) {
        return sponsorRows.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            if (sponsorRows.get(position).getType().equals("col_2")) {
                convertView = inflater.inflate(R.layout.item_sponsor_2, null);
            } else if (sponsorRows.get(position).getType().equals("col_3")) {
                convertView = inflater.inflate(R.layout.item_sponsor_3, null);
            } else {
                convertView = inflater.inflate(R.layout.item_sponsor_1, null);
            }
        }

        if (sponsorRows.get(position).getType().equals("col_2")) {
            TextView title1 = (TextView) convertView.findViewById(R.id.sponsorTitle1);
            ImageView logo1 = (ImageView) convertView.findViewById(R.id.sponsorImage1);

            TextView title2 = (TextView) convertView.findViewById(R.id.sponsorTitle2);
            ImageView logo2 = (ImageView) convertView.findViewById(R.id.sponsorImage2);

            title1.setText(sponsorRows.get(position).getSponsorRow().get(0).getTitle());
            title2.setText(sponsorRows.get(position).getSponsorRow().get(1).getTitle());
            Glide.with(activity)
                    .load(sponsorRows.get(position).getSponsorRow().get(0).getLogo())
                    .thumbnail(0.5f)
                    .into(logo1);
            Glide.with(activity)
                    .load(sponsorRows.get(position).getSponsorRow().get(1).getLogo())
                    .thumbnail(0.5f)
                    .into(logo2);

        } else if (sponsorRows.get(position).getType().equals("col_3")) {
            TextView title1 = (TextView) convertView.findViewById(R.id.sponsorTitle1);
            ImageView logo1 = (ImageView) convertView.findViewById(R.id.sponsorImage1);

            TextView title2 = (TextView) convertView.findViewById(R.id.sponsorTitle2);
            ImageView logo2 = (ImageView) convertView.findViewById(R.id.sponsorImage2);

            TextView title3 = (TextView) convertView.findViewById(R.id.sponsorTitle3);
            ImageView logo3 = (ImageView) convertView.findViewById(R.id.sponsorImage3);

            title1.setText(sponsorRows.get(position).getSponsorRow().get(0).getTitle());
            title2.setText(sponsorRows.get(position).getSponsorRow().get(1).getTitle());
            title3.setText(sponsorRows.get(position).getSponsorRow().get(2).getTitle());
            Glide.with(activity)
                    .load(sponsorRows.get(position).getSponsorRow().get(0).getLogo())
                    .thumbnail(0.5f)
                    .into(logo1);
            Glide.with(activity)
                    .load(sponsorRows.get(position).getSponsorRow().get(1).getLogo())
                    .thumbnail(0.5f)
                    .into(logo2);
            Glide.with(activity)
                    .load(sponsorRows.get(position).getSponsorRow().get(2).getLogo())
                    .thumbnail(0.5f)
                    .into(logo2);

        } else {
            TextView title = (TextView) convertView.findViewById(R.id.sponsorTitle1);
            ImageView logo = (ImageView) convertView.findViewById(R.id.sponsorImage1);

            title.setText(sponsorRows.get(position).getSponsorRow().get(0).getTitle());

            Glide.with(activity)
                    .load(sponsorRows.get(position).getSponsorRow().get(0).getLogo())
                    .thumbnail(0.5f)
                    .into(logo);
        }

        return convertView;
    }

}
