package com.example.foodorder2.Adapter;

import android.animation.Animator;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodorder2.Fragment.HomeFragment;
import com.example.foodorder2.Model.Orders;
import com.example.foodorder2.Model.ProduductModel;
import com.example.foodorder2.R;
import com.example.foodorder2.Util.CircleAnimationUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    Activity context;
    List<ProduductModel> adapterList;
    DatabaseReference reference;
    DatabaseReference referenceProduct, databaseReference, likesReference;
    FirebaseUser firebaseUser;
    Boolean likechecker = false;
    int likecount;
    DatabaseReference likesref;

    public ProductAdapter(Activity context, List<ProduductModel> adapterList) {
        this.context = context;
        this.adapterList = adapterList;
        reference = FirebaseDatabase.getInstance().getReference("Orders");
        referenceProduct = FirebaseDatabase.getInstance().getReference("Products");
        likesReference = FirebaseDatabase.getInstance().getReference("likes");
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.product_rv_item, parent, false);
        return new ProductViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        ProduductModel produductModel = adapterList.get(position);

        holder.imageView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.translet_animation));
        holder.conrainer.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scal_anmiate));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = firebaseUser.getUid();
        final String postKey = produductModel.getId();

        holder.nameTxt.setText(produductModel.getName());
        holder.detailsTxt.setText(produductModel.getDetails());
        holder.sumTxt.setText(produductModel.getSum());
        Glide.with(context)
                .load(produductModel.getImageUri())
                .into(holder.imageView);


        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView destView = context.findViewById(R.id.shopping);

                new CircleAnimationUtil().attachActivity(context).setTargetView(holder.imageView).setMoveDuration(1500).setDestView(destView).setAnimationListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        holder.imageView.setVisibility(View.VISIBLE);
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

                Orders order = new Orders(produductModel.getId(), 0);
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


        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likechecker = true;

                likesReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (likechecker.equals(true)){
                            if (snapshot.child(postKey).hasChild(currentUserId)){
                                likesReference.child(postKey).child(currentUserId).removeValue();
                                likechecker=false;
                            }
                            else {
                                likesReference.child(postKey).child(currentUserId).setValue(true);
                                likechecker=false;
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        holder.setLikesButtonStatus(postKey);





    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView nameTxt, detailsTxt, sumTxt,likesDisple;
        ImageView imageView, plus, likeBtn;
        ConstraintLayout conrainer;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTxt = itemView.findViewById(R.id.name_cart);
            detailsTxt = itemView.findViewById(R.id.details_cart);
            sumTxt = itemView.findViewById(R.id.summa);
            imageView = itemView.findViewById(R.id.imageCart);
            plus = itemView.findViewById(R.id.plus);
            conrainer = itemView.findViewById(R.id.constraintLayout);
            likeBtn = itemView.findViewById(R.id.like);
            likesDisple=itemView.findViewById(R.id.like_text);

        }

        public void setLikesButtonStatus(final String postKey) {
            likeBtn = itemView.findViewById(R.id.like);
            likesref = FirebaseDatabase.getInstance().getReference("likes");
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String userId = user.getUid();
            String likes = "likes";
            likesref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child(postKey).hasChild(userId)) {
                        likecount = (int) snapshot.child(postKey).getChildrenCount();
                        likeBtn.setImageResource(R.drawable.ic_baseline_favorite_24);
                        likesDisple.setText(Integer.toString(likecount)+likes);
                    } else {
                        likecount = (int) snapshot.child(postKey).getChildrenCount();
                        likeBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                        likesDisple.setText(Integer.toString(likecount)+likes);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }


}
