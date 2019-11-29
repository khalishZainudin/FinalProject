package my.edu.fsktm.um.finalproject;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MessagesAdapter adapter;
    private List<Messages> messagesList;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        messagesList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView_messages);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        adapter = new MessagesAdapter(this, messagesList);

        recyclerView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();

        db.collection("sports").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){

                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for(DocumentSnapshot d : list){

                                Messages m = d.toObject(Messages.class);
                                messagesList.add(m);

                            }
                            adapter.notifyDataSetChanged();
                        }


                    }
                });
    }
}
