package my.edu.fsktm.um.finalproject.ForumPage;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.emoji.widget.EmojiTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Date;

import my.edu.fsktm.um.finalproject.ForumPage.Fragment.AddReplyFragment;

import my.edu.fsktm.um.finalproject.R;

public class MessagesAdapter extends FirestoreRecyclerAdapter<Messages, MessagesAdapter.MessagesHolder>  {
    private ForumInterface mContext; //instance variable
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MessagesAdapter context;
    Bundle extrasFromInterface;
    FirebaseAuth mAuth;
    String realEmail;
    String forum_type;
    String forum_doc_id;


    public MessagesAdapter(ForumInterface context, @NonNull FirestoreRecyclerOptions<Messages> options, Bundle bundle,String forumType,String forumId) {
        super(options);
        extrasFromInterface = bundle;
        this.mContext = context;
        this.forum_type = forumType;
        this.forum_doc_id = forumId;
    }

    @Override
    protected void onBindViewHolder(final MessagesHolder holder, int position, final Messages model) {

        final String id = getSnapshots().getSnapshot(position).getId();

        // Generate a unique id to a container
        final int newContainerId = getUniqueId();

        // Create a string that retrieve the value of email
        final String email = model.getEmail();

        context = this;

        // Set the value to the textview
        holder.textViewMessages.setText(model.getMessages());
        holder.textViewUser.setText(model.getEmail());

        // Create a date format to make tiemstamp in firebase readable
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd yyyy, hh:mm a");
        String uncovertedTimeStamp = (model.getTimePosted().toDate().toString());
        String ConvertedTimeStamp = simpleDateFormat.format(new Date(uncovertedTimeStamp));

        // Set the layout timestamp
        holder.timeStamp.setText(ConvertedTimeStamp);

        holder.bAdd.setVisibility(View.GONE);

        // Make the container for Add Reply Gone first
        holder.container.setVisibility(View.GONE);

        // Check if there is any images in the database
        if(model.getImages()!=null && model.getImages() != ""){
            Picasso.get().load(model.getImages()).fit().into(holder.ivPicture);
        }
        else{
            holder.ivPicture.setVisibility(View.GONE);
        }

        // Create a string to hold the value of user id
        final String usern;
        usern = mAuth.getInstance().getCurrentUser().getUid();


        //Compare size and add button at buttom of view
        if(position==getItemCount()-1){
            holder.bAdd.setVisibility(View.VISIBLE);
        }

        // Create a listener to the add button
        holder.bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.bAdd.setVisibility(View.GONE);
                holder.container.setId(newContainerId);
                holder.container.setVisibility(View.VISIBLE);
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                Fragment replyFrag = new AddReplyFragment();
                Bundle extras = new Bundle();
                extras.putString("EMAIL",email);
                replyFrag.setArguments(extras);
                replyFrag.setArguments(extrasFromInterface);
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(newContainerId,replyFrag,"first").addToBackStack("second").commit();
            }
        });
        holder.container.setVisibility(View.VISIBLE);

        // Make a function to get email of current user from the cloud firestore
        DocumentReference documentReference = db.collection("Users_Profile").document(usern);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot!=null){
                        realEmail = documentSnapshot.getString("email");
                    }
                }
            }
        });

        // Create a listener to the delete button
        holder.bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(realEmail.equals(model.getEmail())){
                    db.collection(forum_type).document(forum_doc_id).collection("messages").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(mContext, "Successful Deleted", Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        }});
    }

    public int getUniqueId(){
        return (int) SystemClock.currentThreadTimeMillis();
    }

    @NonNull
    @Override
    public MessagesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewforuminterface,parent,false);
        EmojiCompat.init(new BundledEmojiCompatConfig(view.getContext()));
        context = this;
        return new MessagesHolder(view);
    }

    class MessagesHolder extends RecyclerView.ViewHolder{
        TextView textViewMessages;
        TextView textViewUser;
        TextView timeStamp;
        Button bAdd;
        Button bCancel;
        ImageButton bDelete;
        ImageView ivPicture;

        FrameLayout container;
        public MessagesHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessages = itemView.findViewById(R.id.tvDescription_reply);
            textViewUser = itemView.findViewById(R.id.tvUsername);
            timeStamp = itemView.findViewById(R.id.tvTimestampMessages);
            bAdd = itemView.findViewById(R.id.bAdd);
            bCancel = itemView.findViewById(R.id.bCancel);
            bDelete = itemView.findViewById(R.id.ivDelete);
            container = itemView.findViewById(R.id.replyFragment);
            ivPicture = itemView.findViewById(R.id.ivPictures);

        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

}

