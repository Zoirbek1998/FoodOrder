package com.example.foodorder2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder2.Model.Comment;
import com.example.foodorder2.R;
import com.example.foodorder2.ViewHolder.CommentsViewHomder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentActivity extends AppCompatActivity {

    CircleImageView imgHaqida;
    TextView txtName;
    EditText addComment;
    ImageButton post;
    ImageView imageProfile;
    RecyclerView recyclerView;
    DatabaseReference databaseReference, postref;
    Comment comments;


    String postKey;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        initViews();




        comments = new Comment();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        postref = FirebaseDatabase.getInstance().getReference().child("Products").child(postKey).child("comments");

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        user = FirebaseAuth.getInstance().getCurrentUser();
        final String userId = user.getUid();
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String userPhone = snapshot.child("phone").getValue().toString();
                            CommentFeature(userPhone);
                            addComment.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                if (addComment.getText().toString().equals("")) {
                    Toast.makeText(CommentActivity.this, "Siz bo'sh sharh yuborolmaysiz", Toast.LENGTH_SHORT).show();
                } else {
                    addComent();
                }
            }

            private void CommentFeature(String userPhone) {

                String coment_post = addComment.getText().toString();
                if (coment_post.isEmpty()) {
                    Toast.makeText(CommentActivity.this, "Please write a comment", Toast.LENGTH_SHORT).show();
                } else {
                    Calendar callFordata = Calendar.getInstance();
                    SimpleDateFormat currentdata = new SimpleDateFormat("dd-MMMM-yyyy");
                    final String savecurrentData = currentdata.format(callFordata.getTime());

                    Calendar callfortime = Calendar.getInstance();
                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
                    final String savecurrenttime = currentTime.format(callfortime.getTime());

                    final String randomkey = userId + savecurrentData + savecurrenttime;

                    HashMap commentMap = new HashMap();
                    commentMap.put("id", userId);
                    commentMap.put("comment", coment_post);
                    commentMap.put("data", savecurrentData);
                    commentMap.put("time", savecurrenttime);
                    commentMap.put("phone", userPhone);

                    postref.child(randomkey).updateChildren(commentMap)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(CommentActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(CommentActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                }
            }
        });


    }


    private void addComent() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(postKey);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("comment", addComment.getText().toString());
        hashMap.put("publisher", user.getUid());

        reference.push().setValue(hashMap);
        addComment.setText("");

    }


    private void initViews() {

        imgHaqida = findViewById(R.id.image_nimaligi);
        txtName = findViewById(R.id.nameComen);
        addComment = findViewById(R.id.add_coment);
        post = findViewById(R.id.post);
        imageProfile = findViewById(R.id.image_profile);
        recyclerView = findViewById(R.id.recyclerComment);
        recyclerView.setHasFixedSize(true);


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Comment> options = new FirebaseRecyclerOptions.Builder<Comment>()
                .setQuery(postref, Comment.class)
                .build();

        FirebaseRecyclerAdapter<Comment, CommentsViewHomder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Comment, CommentsViewHomder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CommentsViewHomder holder, int position, @NonNull Comment model) {

                        holder.Comment(getApplication(), model.getComment(), model.getData(), model.getTime(), model.getPhone());

                    }

                    @NonNull
                    @Override
                    public CommentsViewHomder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);

                        return new CommentsViewHomder(view);
                    }
                };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
}