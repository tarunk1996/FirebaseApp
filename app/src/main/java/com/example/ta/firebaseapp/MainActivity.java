package com.example.ta.firebaseapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private Button mFirebaseBtn;
    private EditText nameFetch;
    private EditText emailFetch;
    private DatabaseReference mDatabase;
    private TextView nameview,emailview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseBtn = (Button) findViewById(R.id.firebaseButton);
        nameFetch = (EditText) findViewById(R.id.name);
        emailFetch = (EditText) findViewById(R.id.email);

        nameview = (TextView) findViewById(R.id.nameview);
        emailview = (TextView) findViewById(R.id.emailview);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Name");

        mFirebaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameFetch.getText().toString().trim();
                String email = emailFetch.getText().toString().trim();

                HashMap<String,String> hashMap = new HashMap<String, String>();

                hashMap.put("Name",name);
                hashMap.put("Email",email);


                mDatabase.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful())
                       {
                           Toast.makeText(MainActivity.this,"Stored",Toast.LENGTH_LONG).show();
                       }
                       else
                       {
                           Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
                       }
                    }
                });
            }
        });

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString();
                nameview.setText("Name : "+name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


}
