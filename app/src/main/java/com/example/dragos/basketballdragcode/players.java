package com.example.dragos.basketballdragcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dragos on 2016-09-07.
 */
public class players{
    private ArrayList<Player> list;

    public players() {
        list = new ArrayList<Player>();
        list.add(new Player("dragos","4388257387"));
        list.add(new Player("dragos","4388257387"));
        list.add(new Player("dragos","4388257387"));
        list.add(new Player("dragos","4388257387"));
        list.add(new Player("dragos","4388257387"));
        list.add(new Player("dragos","4388257387"));

    }

    public ArrayList<Player> getList() {
        return list;
    }
}