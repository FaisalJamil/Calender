/*
 * Copyright (c) 2017. Faisal Jamil
 */

package com.example.faisal.interviewtest.feature.list.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.faisal.domain.models.BirthDayRecord;
import com.example.faisal.interviewtest.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

// Create the basic Post adapter extending from RecyclerView.Adapter
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    // Store a member variable for the Birthday list
    private List<BirthDayRecord> bdList = new ArrayList<>();
    // Store the context for easy access
    private Context mContext;

    // General constructor
    public ListAdapter(Context context) {
        mContext = context;
    }

    public void setItemList(List<BirthDayRecord> bdList) {
        this.bdList = bdList;
    }

    public BirthDayRecord getItemByPosition(int position) {
        return bdList.get(position);
    }

    public void addRecord(BirthDayRecord record) {
        bdList.add(record);
        notifyItemRangeChanged(bdList.size()-1, 1);
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.birthday_record, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // Get the data model based on position
        BirthDayRecord record = bdList.get(position);
        viewHolder.name.setText(record.name);
        viewHolder.date.setText(record.date);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return bdList.size();
    }

    // This class can also be written outside parent class if the data increases
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.date)
        TextView date;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
