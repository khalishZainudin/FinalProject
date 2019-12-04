package my.edu.fsktm.um.finalproject.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import my.edu.fsktm.um.finalproject.ForumTitle.Forum;
import my.edu.fsktm.um.finalproject.ForumTitle.ForumAdapter;
import my.edu.fsktm.um.finalproject.ForumTitle.ForumInterface;
import my.edu.fsktm.um.finalproject.R;

public class TechnicalSupportFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("Technical");
    private ForumAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_technical_support,container,false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Query query = userRef.orderBy("DatePosted",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Forum> options = new
                FirestoreRecyclerOptions.Builder<Forum>()
                .setQuery(query,Forum.class)
                .build();
        adapter = new ForumAdapter(options);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.rvTech);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ForumAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Forum forum = documentSnapshot.toObject(Forum.class);
                String title = forum.getTitle();
                String description = forum.getDescription();
                String username = forum.getUser();
                String id = documentSnapshot.getId();
                Intent intent = new Intent(getActivity(), ForumInterface.class);
                Bundle extras = new Bundle();
                extras.putString("FORUM_TYPE","Review");
                extras.putString("FORUM_ID",id);
                extras.putString("TITLE",title);
                extras.putString("DESCRIPTION",description);
                extras.putString("USER",username);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}

