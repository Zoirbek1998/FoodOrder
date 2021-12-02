package com.example.foodorder2.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorder2.Data.Reference;
import com.example.foodorder2.Model.User;
import com.example.foodorder2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchListener;

public class FioActivity extends AppCompatActivity {

    ImageView sun, dayLand, nightLand;
    View daySky, nightSky;
    DayNightSwitch dayNightSwitch;
    EditText edtName, edtSurName;
    Button btnFio;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fio);

        initViews();

        reference = FirebaseDatabase.getInstance().getReference(Reference.USER);
        btnFio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String surname = edtSurName.getText().toString();
                String phone = getIntent().getExtras().getString("phone");
                String imageUri="";

                User user = new User(name, surname, phone,imageUri);

                reference.child(phone).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            sendPhone(phone);
                        } else {
                            Toast.makeText(FioActivity.this, "Feiled", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        dayNightSwitch.setListener(new DayNightSwitchListener() {
            @Override
            public void onSwitch(boolean is_night) {
                if (is_night) {

                    sun.animate().translationY(110).setDuration(1000);
                    nightLand.animate().alpha(0).setDuration(1300);
                    daySky.animate().alpha(0).setDuration(1300);
                } else {
                    sun.animate().translationY(-110).setDuration(1000);
                    nightLand.animate().alpha(1).setDuration(1300);
                    daySky.animate().alpha(1).setDuration(1300);
                }
            }
        });
    }

    private void sendPhone(String phone) {

        SharedPreferences.Editor editor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
        editor.putString("phone", phone);
        Intent intent = new Intent(FioActivity.this, MenuLeftActivity.class);
        startActivity(intent);
        finish();

    }

    private void initViews() {

        sun = findViewById(R.id.sun);
        dayLand = findViewById(R.id.day_landscape);
        nightLand = findViewById(R.id.night_landscape);
        daySky = findViewById(R.id.day_bg);
        nightSky = findViewById(R.id.night_bg);
        dayNightSwitch = findViewById(R.id.day_night_switch);
        edtName = findViewById(R.id.edtName);
        edtSurName = findViewById(R.id.edtSurNam);
        btnFio = findViewById(R.id.btnfio);
    }
}