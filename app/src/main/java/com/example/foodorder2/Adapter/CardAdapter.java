package com.example.foodorder2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorder2.Model.Orders;
import com.example.foodorder2.Model.ProduductModel;
import com.example.foodorder2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    Context context;
    List<Orders> ordersList;
    DatabaseReference reference;
    DatabaseReference referenceOrders;

    public CardAdapter(Context context, List<Orders> ordersList) {
        this.context = context;
        this.ordersList = ordersList;
        reference = FirebaseDatabase.getInstance().getReference("Products");
        referenceOrders = FirebaseDatabase.getInstance().getReference("Orders");
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_view, parent, false);

        return new CardViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

        String id = ordersList.get(position).getProduct_id();
        reference.orderByChild("id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    ProduductModel produductModel = dataSnapshot.getValue(ProduductModel.class);
                    holder.nameCart.setText(produductModel.getName());
                    holder.detailsCart.setText(produductModel.getDetails());
                    holder.summCart.setText(produductModel.getSum());
                    Glide.with(context)
                            .load(produductModel.getImageUri())
                            .into(holder.imageCart);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.soniCart.setText(ordersList.get(position).getCount() + "");
        holder.plusCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int c = ordersList.get(position).getCount() + 1;

                Orders orders = new Orders(id,c);
                ordersList.set(position, orders);
                referenceOrders.child(id).setValue(orders).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            holder.soniCart.setText(ordersList.get(position).getCount() + "");
                        }

                    }
                });

            }
        });

        Locale locale = new Locale("en","US");
        NumberFormat fm=NumberFormat.getCurrencyInstance(locale);
//        int price=(Integer.parseInt(ordersList.get(position).getSumm()))*(Integer.valueOf(ordersList.get(position).getCount()));
//        holder.soniCart.setText(fm.format(price));


    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder {

        TextView nameCart, detailsCart, summCart,soniCart;
        ImageView imageCart;
        CardView plusCart, minusCart;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            nameCart = itemView.findViewById(R.id.name_cart);
            detailsCart = itemView.findViewById(R.id.details_cart);
            plusCart = itemView.findViewById(R.id.cart_plus);
            minusCart = itemView.findViewById(R.id.cart_minus);
            soniCart = itemView.findViewById(R.id.cart_soni);
            imageCart = itemView.findViewById(R.id.img_Cart);
            summCart = itemView.findViewById(R.id.summa_cart);
        }
    }

}
