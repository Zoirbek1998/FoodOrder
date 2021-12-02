package com.example.foodorder2.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodorder2.Fragment.OnBoarding1Fragment;
import com.example.foodorder2.Fragment.OnBoarding2Fragment;
import com.example.foodorder2.Fragment.OnBoarding3Fragment;
import com.example.foodorder2.R;

public class IntroductoryActivity extends AppCompatActivity {
    ImageView logo ,spashImage;
    TextView appName;
    LottieAnimationView lottieAnimationView;
    Animation anim;

    private static final int NUM_PAGES=3;
    private ViewPager viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);


        logo=findViewById(R.id.logo);
        spashImage=findViewById(R.id.img);
        appName=findViewById(R.id.appname);
        lottieAnimationView=findViewById(R.id.animationView);

        viewPager=findViewById(R.id.pager);
        pagerAdapter=new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        anim= AnimationUtils.loadAnimation(this,R.anim.o_b_anim);
        viewPager.startAnimation(anim);

        spashImage.animate().translationY(-2200).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(2000).setDuration(1000).setStartDelay(4000);
        appName.animate().translationY(2000).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(2000).setDuration(1000).setStartDelay(4000);

    }
    public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }




        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    OnBoarding1Fragment tab1=new OnBoarding1Fragment();
                    return  tab1;
                case 1:
                    OnBoarding2Fragment tab2=new OnBoarding2Fragment();
                    return tab2;
                case 2:
                    OnBoarding3Fragment tab3=new OnBoarding3Fragment();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}