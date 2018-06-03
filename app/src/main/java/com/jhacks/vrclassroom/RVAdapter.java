package com.jhacks.vrclassroom;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    private ArrayList<String> mDataset; //data is a list of JSON Objects from Firebase
    private RecycleViewClickListener mListener;

    public RVAdapter(ArrayList<String> myDataset, RecycleViewClickListener listener) {
        this.mListener = listener;
        this.mDataset = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View vw = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.questions_row_layout, parent, false);
        ViewHolder vh = new ViewHolder(vw, mListener);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTextView;
        private RecycleViewClickListener mListener;

        public ViewHolder (View v, RecycleViewClickListener listener) {
            super(v);
            mListener = listener;
            v.setOnClickListener(this);
            this.mTextView = (TextView) v.findViewById(R.id.questionText);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition(), mDataset.get(getAdapterPosition()));
            //System.out.println("CLICKED ITEM STRING: " + mDataset.get(getAdapterPosition()));
        }
    }
}
