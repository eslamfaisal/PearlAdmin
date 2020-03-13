package com.pearladmin.android.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.firebase.firestore.Query;
import com.pearladmin.android.R;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InvestmentBoxesAdapter extends FirestoreAdapter<BoxViewHolder> {


    public InvestmentBoxesAdapter(Query query) {
        super(query);
    }

    @NonNull
    @Override
    public BoxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BoxViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.box_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BoxViewHolder holder, int position) {
        holder.setHolderData(getSnapshot(position));
    }

}
