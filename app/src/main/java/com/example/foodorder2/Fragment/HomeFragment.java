package com.example.foodorder2.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.foodorder2.Activity.CartActivity;
import com.example.foodorder2.Activity.SearchActivity;
import com.example.foodorder2.Adapter.ProductAdapter;
import com.example.foodorder2.Adapter.StaticRvAdapter;
import com.example.foodorder2.Data.Reference;
import com.example.foodorder2.Listner.StatListner;
import com.example.foodorder2.Model.Categories;
import com.example.foodorder2.Model.Discount;
import com.example.foodorder2.Model.Orders;
import com.example.foodorder2.Model.ProduductModel;
import com.example.foodorder2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements StatListner {

    RecyclerView recycler_menu, recycler_producy;
    StaticRvAdapter staticRvAdapter;
    DatabaseReference reference;
    TextView tvTxtContent;
    ImageView shopping,search;
    FirebaseDatabase database;

    StatListner listner;
    List<Categories> categoriesList = new ArrayList<>();
    List<ProduductModel> produductList = new ArrayList<>();

    private ArrayList<Discount> discountList;
    ImageSlider mainsSlider;
    public static String PHONE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listner = this::reLoad;

        initVars();
        initViews(view);
        loadStaticRv();
        loadProduct();

        PHONE = getPhone();
        getCart();

        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CartActivity.class));
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });

        final List<SlideModel> remoteimages = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("Discounts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren())
                            remoteimages.add(new SlideModel(data.child("image").getValue().toString(), data.child("name").getValue().toString(), ScaleTypes.FIT));

                        mainsSlider.setImageList(remoteimages, ScaleTypes.FIT);

                        mainsSlider.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onItemSelected(int i) {
                                Toast.makeText(getContext(), remoteimages.get(i).getTitle().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        return view;
    }

    private void loadProductById(String cat_id) {

        reference.child(Reference.PRODUCT).orderByChild("cat_id").equalTo(cat_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                produductList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProduductModel produductModel = dataSnapshot.getValue(ProduductModel.class);
                    produductList.add(produductModel);

                }
                recycler_producy.setAdapter(new ProductAdapter((Activity) getContext(), produductList));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void loadProduct() {
        reference.child(Reference.PRODUCT).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                produductList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProduductModel produductModel = dataSnapshot.getValue(ProduductModel.class);
                    produductList.add(produductModel);
                }
                ProductAdapter adapter = new ProductAdapter(getActivity(), produductList);
                recycler_producy.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadStaticRv() {
        reference.child(Reference.CATEGORY).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoriesList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Categories categories = dataSnapshot.getValue(Categories.class);
                    categoriesList.add(categories);
                }
                if (categoriesList.size() > 0) {
                    categoriesList.add(0, new Categories("sdfdsf", "All", "All","",""));
                }

                recycler_menu.setAdapter(new StaticRvAdapter(categoriesList, (Activity) getContext(), listner));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void initVars() {
        reference = FirebaseDatabase.getInstance().getReference();
    }


    private void initViews(View v) {
        recycler_menu = v.findViewById(R.id.rv_1);
        recycler_producy = v.findViewById(R.id.rv_2);
        mainsSlider = v.findViewById(R.id.image_slider);
        tvTxtContent = v.findViewById(R.id.counter);
        shopping = v.findViewById(R.id.shopping);
        search = v.findViewById(R.id.search);
    }

    public String getPhone() {
        String phone = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE).getString("phone", "99899");
        return phone;
    }

    public void getCart() {
        String phone = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE).getString("phone", "99899");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Orders");
        reference.child(phone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int summa = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Orders order = dataSnapshot.getValue(Orders.class);
                    int count = order.getCount();
                    summa = summa + count;
                }
                tvTxtContent.setText(summa + "");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void reLoad(String cat_id, int position) {
        if (position == 0) {
            loadProduct();
        } else {
            loadProductById(cat_id);
        }
    }
}