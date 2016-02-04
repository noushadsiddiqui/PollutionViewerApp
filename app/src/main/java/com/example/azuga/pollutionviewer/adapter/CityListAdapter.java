package com.example.azuga.pollutionviewer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.azuga.pollutionviewer.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 09-01-2016.
 */
public class CityListAdapter extends BaseAdapter {

    ArrayList<HashMap<String, String>> myList;
    Context context;
    LayoutInflater inflater;

    public CityListAdapter(Context context, ArrayList<HashMap<String, String>> myList) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.myList = myList;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        MyViewHolder myViewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.list_cities, viewGroup, false);
            myViewHolder = new MyViewHolder(view);
            view.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) view.getTag();
        }
        HashMap<String, String> stateMap = myList.get(position);
        String stateName = stateMap.get(position);
        myViewHolder.stateName.setText(stateName);
        return view;
    }

    private class MyViewHolder {
        TextView stateName;

        public MyViewHolder(View item) {
            stateName = (TextView) item.findViewById(R.id.text_cityName);
        }

    }
}
