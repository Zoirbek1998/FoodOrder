package com.example.foodorder2.Activity;

import android.app.ProgressDialog;
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

import com.example.foodorder2.Fragment.HomeFragment;
import com.example.foodorder2.Model.User;
import com.example.foodorder2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchListener;

public class SignUpActivity extends AppCompatActivity {

    ImageView sun, dayLand, nightLand;
    View daySky, nightSky;
    DayNightSwitch dayNightSwitch;
    EditText edtCode, edtTel, edtName;
    Button btnSignUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViews();
        chakUser();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tabel_users = database.getReference("Users");

        sun.setTranslationY(-110);

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

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ProgressDialog mDialog = new ProgressDialog(SignUpActivity.this);
//                mDialog.setMessage("Please wait !!!");
//                mDialog.show();
                String phone = edtTel.getText().toString();
                Intent intent=new Intent(SignUpActivity.this,SignInActivity.class);
                intent.putExtra("phone",phone);
                startActivity(intent);
                finish();

            }
        });


    }
    private void chakUser() {

        SharedPreferences preferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        String phone = preferences.getString("phone", "");
        if (!phone.equals("")) {
            startActivity(new Intent(this,MenuLeftActivity.class));
            finish();
        }
    }

    private void initViews() {

        sun = findViewById(R.id.sun);
        dayLand = findViewById(R.id.day_landscape);
        nightLand = findViewById(R.id.night_landscape);
        daySky = findViewById(R.id.day_bg);
        nightSky = findViewById(R.id.night_bg);
        dayNightSwitch = findViewById(R.id.day_night_switch);
        edtName = findViewById(R.id.edtName);
        edtTel = findViewById(R.id.edtTEl);
        btnSignUp = findViewById(R.id.btnUp);


    }
}

