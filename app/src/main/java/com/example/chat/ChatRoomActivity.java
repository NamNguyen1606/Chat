package com.example.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatRoomActivity extends AppCompatActivity {
    private String roomName, userName;
    private TextView tvTittle, tvMessages;
    private EditText edtMessage;
    private Button btnSendMessage;
    private DatabaseReference mReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        tvTittle = (TextView) findViewById(R.id.tvTitle);
        tvMessages = (TextView) findViewById(R.id.tvMessages);
        edtMessage = (EditText) findViewById(R.id.edtMessageContainer);
        btnSendMessage = (Button) findViewById(R.id.btnSendMessage);

        tvMessages.setMovementMethod(new ScrollingMovementMethod());

        Intent intent = getIntent();
        userName = intent.getStringExtra("UserName");
        roomName = intent.getStringExtra("RoomName");

        tvTittle.setText(roomName);
        mReference = FirebaseDatabase.getInstance().getReference().child(roomName);

        btnSendMessage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = edtMessage.getText().toString();
                Messages messages = new Messages(userName, message);
                mReference.push().setValue(messages);
                edtMessage.setText("");
            }
        });

        LoadMessagesData();
    }

    private void LoadMessagesData() {
        mReference = FirebaseDatabase.getInstance().getReference().child(roomName);
        mReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Messages messages = dataSnapshot.getValue(Messages.class);
                tvMessages.append(messages.getUserName() + ": " + messages.getMessage() + "\n \n");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
