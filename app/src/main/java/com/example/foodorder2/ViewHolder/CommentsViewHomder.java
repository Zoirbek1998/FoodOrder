package com.example.foodorder2.ViewHolder;

import android.app.Application;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder2.R;
import com.google.firebase.database.DatabaseReference;

public class CommentsViewHomder extends RecyclerView.ViewHolder {

    TextView textView2,textView1;


    public CommentsViewHomder(@NonNull View itemView) {
        super(itemView);
    }

    public void Comment(Application application,String comment,String data,String time,String phone){
        textView1=itemView.findViewById(R.id.time_data);
        textView2=itemView.findViewById(R.id.time_username_coment);

        textView1.setText(data +"-" +time);
        textView2.setText(phone + ":" + comment);
    }

    public void setLikesButtonStatus(final String postkey) {

    }
}
