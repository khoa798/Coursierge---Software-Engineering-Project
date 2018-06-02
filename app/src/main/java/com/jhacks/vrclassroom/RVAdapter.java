package com.jhacks.vrclassroom;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    private ArrayList<String> mDataset; //data is a list of JSON Objects from Firebase

    public RVAdapter(ArrayList<String> myDataset) {
        this.mDataset = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View vw = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.questions_row_layout, parent, false);
        ViewHolder vh = new ViewHolder(vw);
        return vh;
    }


    @Override
    public void onBindViewHolder(RVAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder (View v) {
            super(v);
            this.mTextView = (TextView) v.findViewById(R.id.questionText);
        }
    }
}
