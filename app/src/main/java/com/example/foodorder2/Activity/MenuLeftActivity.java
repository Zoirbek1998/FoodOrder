package com.example.foodorder2.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodorder2.Fragment.AbautFragment;
import com.example.foodorder2.Fragment.HomeFragment;
import com.example.foodorder2.Fragment.ProfileFragment;
import com.example.foodorder2.Fragment.RestaurantFragment;
import com.example.foodorder2.Fragment.SettingFragment;
import com.example.foodorder2.Model.User;
import com.example.foodorder2.R;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class MenuLeftActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtFullName, txtFullPhone;
    private DuoDrawerLayout drawerLayout;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_left);
        initVars();


        txtFullName = findViewById(R.id.cricle_name);
        txtFullPhone = findViewById(R.id.circle_phone);

//        txtFullName.setText(user.getName());
//        txtFullPhone.setText(user.getPhone());
    }


    private void initVars() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);

        DuoDrawerToggle drawerToggle = new DuoDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        View contentView = drawerLayout.getContentView();
        View menuView = drawerLayout.getMenuView();

        LinearLayout ll_Home = menuView.findViewById(R.id.home_id);
        LinearLayout ll_Profile = menuView.findViewById(R.id.profile_id);
        LinearLayout ll_Restaurats = menuView.findViewById(R.id.restaurant_id);
        LinearLayout ll_Setting = menuView.findViewById(R.id.setting_id);
        LinearLayout ll_About = menuView.findViewById(R.id.abbauts_id);
        LinearLayout ll_Logaut = menuView.findViewById(R.id.logaut_id);


        ll_Home.setOnClickListener(this);
        ll_Profile.setOnClickListener(this);
        ll_Restaurats.setOnClickListener(this);
        ll_Setting.setOnClickListener(this);
        ll_About.setOnClickListener(this);
        ll_Logaut.setOnClickListener(this);

        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            String publisher = intent.getString("publisherid");

            SharedPreferences.Editor editor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
            editor.putString("profileid", publisher);
            editor.apply();


        }

        replace(new HomeFragment());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.home_id:
                replace(new HomeFragment(), "Home");
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                break;

            case R.id.profile_id:
                replace(new ProfileFragment(), "Profile");
                Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show();
                break;

            case R.id.restaurant_id:
                replace(new RestaurantFragment(), "Restaurat");
                Toast.makeText(this, "Restaurant", Toast.LENGTH_SHORT).show();
                break;

            case R.id.setting_id:
                replace(new SettingFragment(), "Setting");
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                break;

            case R.id.abbauts_id:
                replace(new AbautFragment(), "About");
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logaut_id:
                replace(new HomeFragment(), "Home");
                Toast.makeText(this, "Logaut", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer();
    }

    private void replace(Fragment fragment, String s) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.from, fragment);
        transaction.addToBackStack(s);
        transaction.commit();
    }

    private void replace(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.from, fragment);
        transaction.commit();
    }


}