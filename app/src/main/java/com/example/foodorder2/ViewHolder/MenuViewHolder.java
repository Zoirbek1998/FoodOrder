package com.example.foodorder2.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder2.Listner.ItemClicListener;
import com.example.foodorder2.R;


public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView txtMenuName;
    public ImageView imageView;
    ItemClicListener itemClicListener;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);

        txtMenuName = itemView.findViewById(R.id.txtName);
        imageView = itemView.findViewById(R.id.imageSt);
        itemView.setOnClickListener(this);
    }

    public void setItemClicListener(ItemClicListener itemClicListener) {
        this.itemClicListener = itemClicListener;
    }

    @Override
    public void onClick(View v) {
        itemClicListener.onClick(v, getAdapterPosition(), false);
    }
}
