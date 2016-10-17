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
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by Dragos on 2016-09-01.
 */
public class Fragment2  extends Fragment  {

    private ListView lv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment2,container,false);

        lv=(ListView)view.findViewById(R.id.list2);

        final String []WeekDays= {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,WeekDays);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    startActivity(new Intent(getContext(),MondayActivity.class));
                }
                if(position == 1)
                {
                    startActivity(new Intent(getContext(),Tuesday.class));
                }
                if(position == 2)
                {
                    startActivity(new Intent(getContext(),Wednesday.class));
                }
                if(position == 3)
                {

                }
                if(position == 4)
                {

                }
                if(position == 5)
                {

                }
                if(position == 6)
                {

                }


            }
        });



        return view;
    }


}
