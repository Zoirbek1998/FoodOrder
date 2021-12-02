package com.example.foodorder2.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorder2.Common.Common;
import com.example.foodorder2.Data.Reference;
import com.example.foodorder2.Model.User;
import com.example.foodorder2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchListener;

import java.util.concurrent.TimeUnit;

public class SignInActivity extends AppCompatActivity {
    ImageView sun, dayLand, nightLand;
    View daySky, nightSky;
    DayNightSwitch dayNightSwitch;
    EditText edPhone, edtPass;
    Button btnOk;
    String phone;
    FirebaseAuth auth;
    DatabaseReference reference;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    String verfiy_id;
    String otp_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initVars();
        initViews();


//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        final DatabaseReference tabel_users = database.getReference("Users");

//        ProgressDialog mDialog = new ProgressDialog(SignInActivity.this);
//        mDialog.setMessage("Please wait !!!");
//        mDialog.show();

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

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp_id=edtPass.getText().toString();
                PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verfiy_id,otp_id);
                signInWithPhoneNumber(credential);
            }
        });
        sendPhone(phone);


    }

    private void sendPhone(String phone) {
        PhoneAuthOptions options=PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phone)
                .setTimeout(120L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void signInWithPhoneNumber(PhoneAuthCredential credential) {

        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Toast.makeText(SignInActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                    getSharedPreferences("MyPref",MODE_PRIVATE).edit().putString("phone",phone).apply();
                    chaskUser();
                }
                else {
                    Toast.makeText(SignInActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void chaskUser() {
        reference.orderByChild("phone").equalTo(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               if (snapshot.exists()){
                   for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                       User user=dataSnapshot.getValue(User.class);
                       Intent intent=new Intent(SignInActivity.this,MenuLeftActivity.class);
                       intent.putExtra("name",user.getName());
                       startActivity(intent);
                       finish();
                   }
               }
               else {
                    Intent intent=new Intent(SignInActivity.this,FioActivity.class);
                    intent.putExtra("phone",phone);
                    startActivity(intent);
                    finish();
               }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initVars() {
        phone=getIntent().getExtras().getString("phone");
        auth=FirebaseAuth.getInstance();
        reference=FirebaseDatabase.getInstance().getReference(Reference.USER);
        callbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.d("RESPONSE","Complited");
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.d("RESPONSE","Feiled");
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.d("RESPONSE","Code send");
                verfiy_id=s;
            }

        };

    }




    private void  initViews() {
        sun = findViewById(R.id.sun);
        dayLand = findViewById(R.id.day_landscape);
        nightLand = findViewById(R.id.night_landscape);
        daySky = findViewById(R.id.day_bg);
        nightSky = findViewById(R.id.night_bg);
        dayNightSwitch = findViewById(R.id.day_night_switch);
        edtPass = findViewById(R.id.edtPass);
        btnOk = findViewById(R.id.next);

    }

}