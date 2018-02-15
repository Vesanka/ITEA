package com.productions.itea.motivatedev;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ImportantFragment extends Fragment {



    public RecyclerView mRecyclerView;

    private FirebaseDatabase myDb;
    public ImportantAdapter importantAdapter;


    public ImportantFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_trophies, container, false);
        // Firebase Auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser curUser = mAuth.getCurrentUser();
        if (curUser != null) {

            mRecyclerView = (RecyclerView) v.findViewById(R.id.important_rec);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            String uid = curUser.getUid();
            myDb = FirebaseDatabase.getInstance();
            Log.d("DBDBDBDBD", myDb != null ? "OK" : "Oops");
            DatabaseReference solvedTasksRef = myDb.getReference("important_tasks").child(uid);
            importantAdapter = new ImportantAdapter(getActivity(), solvedTasksRef);
            mRecyclerView.setAdapter(importantAdapter);

        }
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }



}
