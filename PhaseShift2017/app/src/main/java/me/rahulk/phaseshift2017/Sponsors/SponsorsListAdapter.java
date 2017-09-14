package me.rahulk.phaseshift2017.Sponsors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

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
        final int pos = position;

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (sponsorRows.get(position).getType().equals("col_2")) {
            convertView = inflater.inflate(R.layout.item_sponsor_2, null);
        } else if (sponsorRows.get(position).getType().equals("col_3")) {
            convertView = inflater.inflate(R.layout.item_sponsor_3, null);
        } else {
            convertView = inflater.inflate(R.layout.item_sponsor_1, null);
        }

        if (sponsorRows.get(position).getType().equals("col_2")) {
            View sponsorView1 = convertView.findViewById(R.id.sponsorView1);
            View sponsorView2 = convertView.findViewById(R.id.sponsorView2);
            if (sponsorRows.get(position).getSponsorRow().size() >= 1) {
                sponsorView1.setVisibility(View.VISIBLE);
                TextView title1 = (TextView) convertView.findViewById(R.id.sponsorTitle1);
                ImageView logo1 = (ImageView) convertView.findViewById(R.id.sponsorImage1);

                title1.setText(sponsorRows.get(position).getSponsorRow().get(0).getTitle());

                GlideApp.with(activity)
                        .load(sponsorRows.get(position).getSponsorRow().get(0).getLogo())
                        .placeholder(R.drawable.loading)
                        .thumbnail(0.5f)
                        .into(logo1);

                sponsorView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri webpage = Uri.parse(sponsorRows.get(pos).getSponsorRow().get(0).getUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                        if (intent.resolveActivity(activity.getPackageManager()) != null) {
                            activity.startActivity(intent);
                        }
                    }
                });
            }
            if (sponsorRows.get(position).getSponsorRow().size() == 2) {
                sponsorView2.setVisibility(View.VISIBLE);
                TextView title2 = (TextView) convertView.findViewById(R.id.sponsorTitle2);
                ImageView logo2 = (ImageView) convertView.findViewById(R.id.sponsorImage2);

                title2.setText(sponsorRows.get(position).getSponsorRow().get(1).getTitle());

                GlideApp.with(activity)
                        .load(sponsorRows.get(position).getSponsorRow().get(1).getLogo())
                        .placeholder(R.drawable.loading)
                        .thumbnail(0.5f)
                        .into(logo2);

                sponsorView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri webpage = Uri.parse(sponsorRows.get(pos).getSponsorRow().get(1).getUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                        if (intent.resolveActivity(activity.getPackageManager()) != null) {
                            activity.startActivity(intent);
                        }
                    }
                });
            } else {
                sponsorView2.setVisibility(View.INVISIBLE);
            }

        } else if (sponsorRows.get(position).getType().equals("col_3")) {
            View sponsorView1 = convertView.findViewById(R.id.sponsorView1);
            View sponsorView2 = convertView.findViewById(R.id.sponsorView2);
            View sponsorView3 = convertView.findViewById(R.id.sponsorView3);

            if (sponsorRows.get(position).getSponsorRow().size() >= 1) {
                sponsorView1.setVisibility(View.VISIBLE);
                TextView title1 = (TextView) convertView.findViewById(R.id.sponsorTitle1);
                ImageView logo1 = (ImageView) convertView.findViewById(R.id.sponsorImage1);

                title1.setText(sponsorRows.get(position).getSponsorRow().get(0).getTitle());

                GlideApp.with(activity)
                        .load(sponsorRows.get(position).getSponsorRow().get(0).getLogo())
                        .placeholder(R.drawable.loading)
                        .thumbnail(0.5f)
                        .into(logo1);
                sponsorView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri webpage = Uri.parse(sponsorRows.get(pos).getSponsorRow().get(0).getUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                        if (intent.resolveActivity(activity.getPackageManager()) != null) {
                            activity.startActivity(intent);
                        }
                    }
                });
            }
            if (sponsorRows.get(position).getSponsorRow().size() >= 2) {
                sponsorView2.setVisibility(View.VISIBLE);
                TextView title2 = (TextView) convertView.findViewById(R.id.sponsorTitle2);
                ImageView logo2 = (ImageView) convertView.findViewById(R.id.sponsorImage2);

                title2.setText(sponsorRows.get(position).getSponsorRow().get(1).getTitle());

                GlideApp.with(activity)
                        .load(sponsorRows.get(position).getSponsorRow().get(1).getLogo())
                        .placeholder(R.drawable.loading)
                        .thumbnail(0.5f)
                        .into(logo2);

                sponsorView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri webpage = Uri.parse(sponsorRows.get(pos).getSponsorRow().get(1).getUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                        if (intent.resolveActivity(activity.getPackageManager()) != null) {
                            activity.startActivity(intent);
                        }
                    }
                });
            } else {
                sponsorView2.setVisibility(View.INVISIBLE);
                sponsorView3.setVisibility(View.INVISIBLE);
            }
            if (sponsorRows.get(position).getSponsorRow().size() == 3) {
                sponsorView3.setVisibility(View.VISIBLE);
                TextView title3 = (TextView) convertView.findViewById(R.id.sponsorTitle3);
                ImageView logo3 = (ImageView) convertView.findViewById(R.id.sponsorImage3);

                title3.setText(sponsorRows.get(position).getSponsorRow().get(2).getTitle());

                GlideApp.with(activity)
                        .load(sponsorRows.get(position).getSponsorRow().get(2).getLogo())
                        .placeholder(R.drawable.loading)
                        .thumbnail(0.5f)
                        .into(logo3);

                sponsorView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri webpage = Uri.parse(sponsorRows.get(pos).getSponsorRow().get(2).getUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                        if (intent.resolveActivity(activity.getPackageManager()) != null) {
                            activity.startActivity(intent);
                        }
                    }
                });
            } else {
                sponsorView3.setVisibility(View.INVISIBLE);
            }

        } else {
            View sponsorView1 = convertView.findViewById(R.id.sponsorView1);
            TextView title = (TextView) convertView.findViewById(R.id.sponsorTitle1);
            ImageView logo = (ImageView) convertView.findViewById(R.id.sponsorImage1);

            title.setText(sponsorRows.get(position).getSponsorRow().get(0).getTitle());

            GlideApp.with(activity)
                    .load(sponsorRows.get(position).getSponsorRow().get(0).getLogo())
                    .placeholder(R.drawable.loading)
                    .thumbnail(0.5f)
                    .into(logo);

            sponsorView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri webpage = Uri.parse(sponsorRows.get(pos).getSponsorRow().get(0).getUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    if (intent.resolveActivity(activity.getPackageManager()) != null) {
                        activity.startActivity(intent);
                    }
                }
            });
        }

        return convertView;
    }

}
