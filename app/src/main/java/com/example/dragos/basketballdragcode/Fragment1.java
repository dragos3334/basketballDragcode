package com.example.dragos.basketballdragcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Dragos on 2016-10-16.
 */

public class Fragment1 extends Fragment {


    private Button logOut;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.fragment1,container,false);


        logOut=(Button)view.findViewById(R.id.logout);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == logOut){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getContext(),LoginActivity.class));
                }
            }
        });

        playerAdapter adapter = new playerAdapter(getActivity(),ProfilActivity.Players);

        ListView listView = (ListView) view.findViewById(R.id.list);

        listView.setAdapter(adapter);
        return view;
    }

 }

