package com.example.foodorder2.Adapter;

import android.animation.Animator;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorder2.Fragment.HomeFragment;
import com.example.foodorder2.Model.Orders;
import com.example.foodorder2.Model.ProduductModel;
import com.example.foodorder2.R;
import com.example.foodorder2.Util.CircleAnimationUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{
    Activity context;
    List<ProduductModel> productList;
    DatabaseReference reference;
    DatabaseReference referenceProduct;

    public SearchAdapter(Activity context, List<ProduductModel> productList) {
        this.context = context;
        this.productList = productList;
        reference = FirebaseDatabase.getInstance().getReference("Orders");
        referenceProduct = FirebaseDatabase.getInstance().getReference("Products");

    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(context).inflate(R.layout.list_sarch, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        ProduductModel produductModel=productList.get(position);
        holder.txtName.setText(produductModel.getName());
        holder.txtNarx.setText(produductModel.getSum());
        holder.txtDetails.setText(produductModel.getDetails());
        Glide.with(context)
                .load(produductModel.getImageUri())
                .into(holder.imagePro);

        holder.imagePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView destView = context.findViewById(R.id.shopping);

                new CircleAnimationUtil().attachActivity(context).setTargetView(holder.imagePro).setMoveDuration(1500).setDestView(destView).setAnimationListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        holder.imagePro.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).startAnimation();

                Orders order = new Orders(produductModel.getId(),0);
                reference.child(HomeFragment.PHONE).orderByChild("product_id")
                        .equalTo(produductModel.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Orders order1 = null;
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                order1 = snapshot1.getValue(Orders.class);
                            }
                            order1.setCount(order1.getCount() + 1);
                            reference.child(HomeFragment.PHONE).child(produductModel.getId()).setValue(order1);

                        } else {
                            reference.child(HomeFragment.PHONE).child(produductModel.getId()).setValue(order);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder{

        ImageView imagePro, imagePlus;
        TextView txtName, txtNarx,txtDetails;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            imagePro=itemView.findViewById(R.id.imageCart);
            imagePlus=itemView.findViewById(R.id.plus);
            txtName=itemView.findViewById(R.id.name_cart);
            txtNarx=itemView.findViewById(R.id.summa);
            txtDetails=itemView.findViewById(R.id.details_cart);
        }
    }
}
