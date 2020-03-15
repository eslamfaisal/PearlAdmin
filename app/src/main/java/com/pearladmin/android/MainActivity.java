package com.pearladmin.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pearladmin.android.models.InvestmentBox;
import com.pearladmin.android.activities.InvestmentBoxesActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            FirebaseAuth.getInstance().signInWithEmailAndPassword("eslam@mail.com","111111").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                }
            });
        }

        findViewById(R.id.investment_box).setOnClickListener(view -> {
//            addBox();
            startActivity(new Intent(this, InvestmentBoxesActivity.class));

        });

    }

    private static final String TAG = "eslamfaisal";

    private void addBox(){
        InvestmentBox investmentBox = new InvestmentBox();
        investmentBox.setId(FirebaseFirestore.getInstance().collection("boxes").getId());
        investmentBox.setName("Innovamture");

        FirebaseFirestore.getInstance().collection("boxes")
                .add(investmentBox).addOnCompleteListener(task -> {

                });

    }
}
