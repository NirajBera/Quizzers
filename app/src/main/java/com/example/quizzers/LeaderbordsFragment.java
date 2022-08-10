package com.example.quizzers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizzers.databinding.FragmentLeaderbordsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class LeaderbordsFragment extends Fragment {



    public LeaderbordsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentLeaderbordsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =FragmentLeaderbordsBinding.inflate(inflater,container,false);
        FirebaseFirestore database =FirebaseFirestore.getInstance();
        final ArrayList<User> users=new ArrayList<>();
        LeaderboardsAdapter adapter = new LeaderboardsAdapter(getContext(),users);
        //set adapter in  leaderbordadapter
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        database.collection("users")
                //print user in conins desc order
                .orderBy("coins", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                       for (DocumentSnapshot snapshot:queryDocumentSnapshots){
                           User user=snapshot.toObject(User.class);
                           users.add(user);
                       }
                       adapter.notifyDataSetChanged();
                    }
                });





        return binding.getRoot();
    }
}