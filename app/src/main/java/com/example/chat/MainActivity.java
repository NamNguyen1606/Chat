package com.example.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvChatRoom;
    private Button btnNewRoomEnter;
    private EditText edtNewRoomName;
    private ArrayAdapter<String> roomAdapter;
    private ArrayList<String> roomList ;
    private String Username;
    private DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvChatRoom = (ListView) findViewById(R.id.lvChatRoom);
        btnNewRoomEnter = (Button) findViewById(R.id.btnNewRoomEnter);
        edtNewRoomName = (EditText) findViewById(R.id.edtNewRoomName);

        roomList = new ArrayList<>();
        roomAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, roomList);
        lvChatRoom.setAdapter(roomAdapter);

        DialogRequestUserName();
        LoadListRoom();
        // Firebase
        mReference = FirebaseDatabase.getInstance().getReference();

        btnNewRoomEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mReference.child(edtNewRoomName.getText().toString()).setValue("");
                edtNewRoomName.setText("");
            }
        });

        lvChatRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String roomName = roomList.get(i);
                Intent intent = new Intent(getApplicationContext(), ChatRoomActivity.class);
                intent.putExtra("UserName", Username);
                intent.putExtra("RoomName", roomName);
                startActivity(intent);
            }
        });

        lvChatRoom.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final String roomName = roomList.get(i);
                builder.setTitle("Do you want to delete " + roomName + " ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            mReference.child(roomName).removeValue();
                            roomList.remove(i);
                        } catch (Exception e){

                        }

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                return false;
            }
        });
    }

    private void DialogRequestUserName() {
        Context context = this;
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Input Username");
        final EditText editUsername = new EditText(this);
        builder.setView(editUsername);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Username = editUsername.getText().toString();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    private void LoadListRoom(){
        mReference = FirebaseDatabase.getInstance().getReference();
        mReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    roomList.add(dataSnapshot.getKey());
                    roomAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                roomAdapter.notifyDataSetChanged();
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
