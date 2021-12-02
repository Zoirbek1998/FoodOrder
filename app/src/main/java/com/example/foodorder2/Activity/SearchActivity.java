package com.example.foodorder2.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.foodorder2.Adapter.SearchAdapter;
import com.example.foodorder2.Model.ProduductModel;
import com.example.foodorder2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ProduductModel> arrayList;
    DatabaseReference mreference;
    EditText editText;
    LinearLayout cardImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mreference= FirebaseDatabase.getInstance().getReference("Products");

        recyclerView =findViewById(R.id.recyclerSearch);
        editText=findViewById(R.id.edit);
        cardImage=findViewById(R.id.cartImage);

        arrayList=new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);

        cardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, CartActivity.class));
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty()){
                    search(s.toString());
                }
                else {
                    search("");
                }
            }
        });

    }

    private void search(String s) {
        Query query=mreference.orderByChild("name").startAt(s).endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){

                    arrayList.clear();
                    for (DataSnapshot dss : snapshot.getChildren()){

                        final ProduductModel products=dss.getValue(ProduductModel.class);
                        arrayList.add(products);
                    }

                    SearchAdapter adapter=new SearchAdapter(SearchActivity.this,arrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}