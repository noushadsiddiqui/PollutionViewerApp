package com.example.azuga.pollutionviewer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.azuga.pollutionviewer.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by User on 09-01-2016.
 */
public class CityListAdapter extends BaseAdapter {

    String[] myList;
    Context context;
    LayoutInflater inflater;

    public CityListAdapter(Context context, String[] myList) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.myList = myList;
    }

    @Override
    public int getCount() {
        return myList.length;
    }

    @Override
    public Object getItem(int position) {
        return myList[position];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        MyViewHolder myViewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item_city, viewGroup, false);
            myViewHolder = new MyViewHolder(view);
            view.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) view.getTag();
        }
        String cityName = myList[position];
        myViewHolder.cityName.setText(cityName);
        return view;
    }

    private class MyViewHolder {
        TextView cityName;

        public MyViewHolder(View item) {
            cityName = (TextView) item.findViewById(R.id.text_cityName);
        }

    }
}
