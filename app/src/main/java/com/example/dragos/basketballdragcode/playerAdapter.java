package com.example.dragos.basketballdragcode;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

class PlayerAdapter extends ArrayAdapter<Player> {


    PlayerAdapter(Activity context, ArrayList<Player> words) {
        super(context, 0, words);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView.inflate(getContext(),R.layout.list_item,null);


        Player currentPlayer = getItem(position);

        TextView UnameTextView = (TextView) listItemView.findViewById(R.id.Uname);
        UnameTextView.setText(currentPlayer.getName());


        TextView UPhoneNumerTextView = (TextView) listItemView.findViewById(R.id.UPhoneNumber);
        UPhoneNumerTextView.setText(currentPlayer.getPhoneNumber());

        CircleImageView UserProfilPicView = (CircleImageView) listItemView.findViewById(R.id.pro_pic);

        Picasso.with(getContext()).load(currentPlayer.getProfilPic()).noPlaceholder().centerCrop().fit()
                .into(UserProfilPicView);


        return listItemView;
    }


}


