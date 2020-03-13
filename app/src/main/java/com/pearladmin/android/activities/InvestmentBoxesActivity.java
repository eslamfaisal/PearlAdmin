package com.pearladmin.android.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.pearladmin.android.R;
import com.pearladmin.android.adapters.InvestmentBoxesAdapter;


public class InvestmentBoxesActivity extends AppCompatActivity implements EventListener<DocumentSnapshot> {

    private Query mQuery;
    private FirebaseFirestore mFirestore;
    private DocumentReference mCurrentUserRef;
    private ListenerRegistration mUserRegistration;
    private RecyclerView recyclerView;
    private InvestmentBoxesAdapter boxesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment_boxes);

        recyclerView = findViewById(R.id.boxes);
        mFirestore = FirebaseFirestore.getInstance();
        FirebaseFirestore.setLoggingEnabled(true);

        onFilter();

    }

    public void onFilter() {
        // Construct query basic query
        mQuery = mFirestore.collection("boxes");
//        mQuery = mQuery.whereEqualTo("ready", true);

        // RecyclerView
        boxesAdapter = new InvestmentBoxesAdapter(mQuery) {
            @Override
            protected void onDataChanged() {
                // Show/hide content if the query returns empty.
                if (getItemCount() == 0) {
                    Snackbar.make(findViewById(android.R.id.content),
                            "No CVs Yet", Snackbar.LENGTH_LONG).show();

                } else {

                }

                Log.d(TAG, "onDataChanged: adde");
            }

            @Override
            protected void onError(FirebaseFirestoreException e) {
                // Show a snackbar on errors
                Snackbar.make(findViewById(android.R.id.content),
                        "Error Indexing", Snackbar.LENGTH_LONG).show();
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(boxesAdapter);

    }

    private static final String TAG = "eslamfaisal";
    @Override
    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

    }

    @Override
    public void onStart() {
        super.onStart();

        // Start listening for Firestore updates
        if (boxesAdapter != null) {
            boxesAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (boxesAdapter != null) {
            boxesAdapter.stopListening();
        }

    }
}
