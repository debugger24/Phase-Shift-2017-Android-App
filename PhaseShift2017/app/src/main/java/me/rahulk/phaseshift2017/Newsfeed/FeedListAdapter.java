package me.rahulk.phaseshift2017.Newsfeed;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;

import java.util.List;

import me.rahulk.phaseshift2017.AppController;
import me.rahulk.phaseshift2017.Puzzle.Puzzle;
import me.rahulk.phaseshift2017.Quiz.Quiz;
import me.rahulk.phaseshift2017.R;

/**
 * Created by debugger24 on 21/06/17.
 */

public class FeedListAdapter extends BaseAdapter {
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private Activity activity;
    private LayoutInflater inflater;
    private List<FeedItem> feedItems;

    public FeedListAdapter(Activity activity, List<FeedItem> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_feed, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView timestamp = (TextView) convertView.findViewById(R.id.timestamp);
        TextView statusMsg = (TextView) convertView.findViewById(R.id.txtStatusMsg);
        TextView url = (TextView) convertView.findViewById(R.id.txtUrl);
        TextView customURL = (TextView) convertView.findViewById(R.id.txtUrlCustom);
        ImageView profilePic = (ImageView) convertView.findViewById(R.id.profilePic);

        FeedImageView feedImageView = (FeedImageView) convertView.findViewById(R.id.feedImage1);

        FeedItem item = feedItems.get(position);

        name.setText(item.getName());

        // Converting timestamp into x ago format
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(Long.parseLong(item.getTimeStamp()) * 1000, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);
        Log.v("TIME", String.valueOf(System.currentTimeMillis()) + "/" + String.valueOf(item.getTimeStamp()));
        timestamp.setText(timeAgo);

        // Chcek for empty status message
        if (!TextUtils.isEmpty(item.getStatus())) {
            statusMsg.setText(item.getStatus());
            statusMsg.setVisibility(View.VISIBLE);
        } else {
            // status is empty, remove from view
            statusMsg.setVisibility(View.GONE);
        }

        // Checking for null feed url
        if (item.getUrl() != null) {
            url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">" + item.getUrl() + "</a> "));

            // Making url clickable
            url.setMovementMethod(LinkMovementMethod.getInstance());
            url.setVisibility(View.VISIBLE);
        } else {
            // url is null, remove from the view
            url.setVisibility(View.GONE);
        }

        // Checking for null feed Custom URL
        if (item.getCustomURL() != null) {
            customURL.setText(item.getCustomURL());
            if (item.getCustomURL().equals("Click here to Launch Quiz")) {
                customURL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(activity, Quiz.class);
                        activity.startActivity(intent);
                    }
                });
            } else if (item.getCustomURL().equals("Click here to Launch Puzzle")) {
                customURL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(activity, Puzzle.class);
                        activity.startActivity(intent);
                    }
                });
            }
            customURL.setVisibility(View.VISIBLE);
        } else {
            // url is null, remove from the view
            customURL.setVisibility(View.GONE);
        }

        // user profile pic
        Glide.with(activity)
                .load(item.getProfilePic())
                .thumbnail(0.5f)
                .into(profilePic);

        // Feed image
        Log.v("FEED", item.getUrl() + item.getImge());
        if (item.getImge() != null) {
            feedImageView.setImageUrl(item.getImge(), imageLoader);
            feedImageView.setVisibility(View.VISIBLE);
            feedImageView
                    .setResponseObserver(new FeedImageView.ResponseObserver() {
                        @Override
                        public void onError() {
                        }

                        @Override
                        public void onSuccess() {
                        }
                    });
        } else {
            feedImageView.setVisibility(View.GONE);
        }

        return convertView;
    }

}
