package my.edu.fsktm.um.finalproject.ForumTitle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.Date;

import java.util.HashMap;
import java.util.Map;

import my.edu.fsktm.um.finalproject.R;

public class AddForum extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Spinner typeSpinner;
    EditText etTitle;
    EditText etDescription;
    Button bAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_forum);

        typeSpinner = (Spinner)findViewById(R.id.sType);
        etTitle = (EditText)findViewById(R.id.etTitle);
        etDescription =(EditText)findViewById(R.id.etDescription);
        bAdd = (Button)findViewById(R.id.bAdd);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddForum.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Type));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(myAdapter);

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String description = etDescription.getText().toString();
                final String type = typeSpinner.getSelectedItem().toString();
                Timestamp now = Timestamp.now();
                final Map<String, Object> forum = new HashMap<>();
                forum.put("Title",title);
                forum.put("Description",description);
                forum.put("DatePosted",now);
                db.collection(type).add(forum).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"Success Adding",Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(AddForum.this,ForumTitle.class);
                startActivity(intent);
            }
        });

    }
}
