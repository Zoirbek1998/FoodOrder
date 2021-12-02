package com.example.foodorder2.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder2.Adapter.CardAdapter;
import com.example.foodorder2.Fragment.HomeFragment;
import com.example.foodorder2.Model.Orders;
import com.example.foodorder2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference reference;
    List<Orders> ordersList = new ArrayList<>();
    Button imgBack, cardPlase;
    TextView hisob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        reference = FirebaseDatabase.getInstance().getReference("Orders").child(HomeFragment.PHONE);
        recyclerView = findViewById(R.id.cart_recycler);
        imgBack = findViewById(R.id.btnBack);
        hisob = findViewById(R.id.jami);
        cardPlase = findViewById(R.id.cardAdd);

//        loadListCard();
        loadCart();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cardPlase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlyordDialog();

            }
        });

    }

    private void showAlyordDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
        alertDialog.setTitle("One more step!");
        alertDialog.setMessage("Enter your address:");

        final EditText edtAddress = new EditText(CartActivity.this);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtAddress.setLayoutParams(lp);
        alertDialog.setView(edtAddress);
        alertDialog.setIcon(R.drawable.ic_baseline_shopping_cart_24);

//        /alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
////                Request request = new Request(
//                        Common.currentUser.getPhone(),
//                        Common.currentUser.getName(),
//                        edtAddress.getText().toString(),
//                        hisob.getText().toString(),
//                        ordersList
//
//                );
//
//                reference.child(String.valueOf(System.currentTimeMillis())).setValue(request);
//
////                new DataBase(getBaseContext()).cleanCart();
////                Toast.makeText(CartActivity.this, "Thank you , Order Place", Toast.LENGTH_SHORT).show();
////                finish();
//            }
//        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

//    private void loadListCard() {
//        int total = 0;
//        for (Orders orders : ordersList) {
//            total += (Integer.parseInt(orders.getSumm())) * (Integer.valueOf(orders.getCount()));
//            Locale locale = new Locale("en", "US");
//            NumberFormat fm = NumberFormat.getCurrencyInstance(locale);
//            hisob.setText(fm.format(total));
//        }
//
//
//    }

    private void loadCart() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Orders orders = dataSnapshot.getValue(Orders.class);
                    ordersList.add(orders);
                }
                CardAdapter adapter = new CardAdapter(CartActivity.this, ordersList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CartActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}