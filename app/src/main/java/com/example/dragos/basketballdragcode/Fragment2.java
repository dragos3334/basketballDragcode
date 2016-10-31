package com.example.dragos.basketballdragcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Dragos on 2016-09-01.
 */
public class Fragment2  extends Fragment  {

    private ListView lv;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.content_main2,container,false);

        lv=(ListView)view.findViewById(R.id.list2);

        final String []WeekDays= {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,WeekDays);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), WeeKDaysActivity.class);
                    intent.putExtra("POSITION_LIST",position);

                startActivity(intent);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    if (ChekForOrganizer()) {
                        Intent i = new Intent(getContext(), DayOrganizerActivity.class);
                        i.putExtra("POSITION", position);
                        startActivity(i);
                    } else {
                        Intent intent = new Intent(getContext(), WeeKDaysActivity.class);
                        intent.putExtra("POSITION_LIST",position);

                        startActivity(intent);
                    }

                return true;
            }
        });



        return view;
    }

    private boolean ChekForOrganizer(){
        return FirebaseAuth.getInstance().getCurrentUser() == null;
    }


}
