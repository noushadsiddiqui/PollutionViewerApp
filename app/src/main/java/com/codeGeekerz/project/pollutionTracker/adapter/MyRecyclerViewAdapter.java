package com.codeGeekerz.project.pollutionTracker.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.codeGeekerz.project.pollutionTracker.R;
import com.codeGeekerz.project.pollutionTracker.utils.DataObject;

import java.util.ArrayList;

/**
 * Created by User on 02-02-2016.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static MyClickListener myClickListener;
    private ArrayList<DataObject> mDataset;

    public MyRecyclerViewAdapter(ArrayList<DataObject> myDataset) {
        mDataset = myDataset;
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.label.setText(mDataset.get(position).getmText1());
        holder.label.setTextSize(18);
        holder.dateTime.setText(mDataset.get(position).getmText2());
        holder.dateTime.setTextSize(25);
        holder.card.setCardBackgroundColor(mDataset.get(position).getmColor());
        holder.pollutionBoard.setText(mDataset.get(position).getmText3());
        holder.pollutionBoard.setTextSize(15);
        holder.pollutionBoard.setTextColor(mDataset.get(position).getmColor());
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000); //You can manage the time of the blink with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        holder.pollutionBoard.startAnimation(anim);
    }

    public void addItem(DataObject dataObj, int index) {
        mDataset.add(index, dataObj);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label;
        TextView dateTime;
        TextView pollutionBoard;
        CardView card;

        public DataObjectHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card_view_1);
            label = (TextView) itemView.findViewById(R.id.station_heading);
            dateTime = (TextView) itemView.findViewById(R.id.AQI_value);
            pollutionBoard = (TextView) itemView.findViewById(R.id.pollutionBoard);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}
