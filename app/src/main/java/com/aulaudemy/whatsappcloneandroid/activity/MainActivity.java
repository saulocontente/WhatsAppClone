package com.aulaudemy.whatsappcloneandroid.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.aulaudemy.whatsappcloneandroid.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reference.child("pontuação").setValue(100);
    }
}