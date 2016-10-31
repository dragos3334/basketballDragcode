package com.example.dragos.basketballdragcode;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Dragos on 2016-08-31.
 */
public class PlayerAdapter extends ArrayAdapter<Player> {


    private static final String LOG_TAG = PlayerAdapter.class.getSimpleName();





    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param words A List of Word objects to display in a list
     */


    public PlayerAdapter(Activity context, ArrayList<Player> words) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);

    }


    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     *
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView.inflate(getContext(),R.layout.list_item,null);



        // Get the {@link AndroidFlavor} object located at this position in the list
        Player currentWord = getItem(position);


        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView UnameTextView = (TextView) listItemView.findViewById(R.id.Uname);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        UnameTextView.setText(currentWord.getUName());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView UPhoneNumerTextView = (TextView) listItemView.findViewById(R.id.UPhoneNumber);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        UPhoneNumerTextView.setText(currentWord.getUPhoneNumber());

        CircleImageView UserProfilPicView = (CircleImageView) listItemView.findViewById(R.id.pro_pic);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        Picasso.with(getContext()).load(currentWord.getProfifPic()).noPlaceholder().centerCrop().fit()
                .into(UserProfilPicView,new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });


        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }


}


