package me.rahulk.phaseshift2017.About;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import me.rahulk.phaseshift2017.R;

/**
 * Created by rahulkumar on 03/08/17.
 */

public class PersonAdapter extends ArrayAdapter<Person> {
    Person person;

    public PersonAdapter(Context context, List<Person> contacts) {
        super(context, 0, contacts);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView;

        listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_person, parent, false);

        person = getItem(position);

        TextView contactName = (TextView) listItemView.findViewById(R.id.contactName);
        contactName.setText(person.getName());

        TextView contactNumber = (TextView) listItemView.findViewById(R.id.contactNumber);
        contactNumber.setText(person.getMobileNumber());

        ImageView profilePic = (ImageView) listItemView.findViewById(R.id.profilePic);
        profilePic.setImageResource(person.getPic());

        View callButton = listItemView.findViewById(R.id.call);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person currentContact = getItem(position);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + currentContact.getMobileNumber()));
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    Toast.makeText(getContext(), "Failed : Require Permission to Call", Toast.LENGTH_SHORT).show();
                    return;
                }
                getContext().startActivity(callIntent);
            }
        });

        return listItemView;
    }
}
