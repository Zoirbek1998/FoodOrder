package com.example.foodorder2.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorder2.Activity.CommentActivity;
import com.example.foodorder2.Listner.StatListner;
import com.example.foodorder2.Model.Categories;
import com.example.foodorder2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class StaticRvAdapter extends RecyclerView.Adapter<StaticRvAdapter.StaticViHol> {

    List<Categories> categoriesList;
    Activity context;
    StatListner listner;
    int row_index=-1;

    public StaticRvAdapter(List<Categories> categoriesList, Activity context, StatListner listner) {
        this.categoriesList = categoriesList;
        this.context = context;
        this.listner = listner;
    }



    @NonNull
    @Override
    public StaticViHol onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.static_rv_item, parent, false);

        return new StaticViHol(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StaticViHol holder, int position) {

        Categories rvMode = categoriesList.get(position);

        String postkey=categoriesList.get(position).getId();

        if (position == 0) {
            holder.imageSt.setImageDrawable(context.getResources().getDrawable(R.drawable.hammasi));
        }
        else {
            Glide.with(context)
                    .load(rvMode.getImageUri())
                    .into(holder.imageSt);
        }

        holder.txtName.setText(rvMode.getName());

        holder.imageSt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.reLoad(rvMode.getId(),position);
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            row_index=position;
            notifyDataSetChanged();
            }
        });
            if (row_index==position){
                holder.linearLayout.setBackgroundResource(R.drawable.static_rv_selected);
            }
            else {
                holder.linearLayout.setBackgroundResource(R.drawable.static_rv_bg);
            }

            holder.imgComent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,CommentActivity.class);
                    intent.putExtra("postkey", postkey);
                    context.startActivity(intent);
                }
            });


    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class StaticViHol extends RecyclerView.ViewHolder {

        TextView txtName;
        ImageView imageSt,imgComent;
        LinearLayout linearLayout;

        public StaticViHol(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            imageSt = itemView.findViewById(R.id.imageSt);
            imgComent = itemView.findViewById(R.id.comentImg);
            linearLayout=itemView.findViewById(R.id.linearLayout);


        }
    }


}
